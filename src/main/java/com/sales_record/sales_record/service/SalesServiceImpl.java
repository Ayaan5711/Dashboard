package com.sales_record.sales_record.service;

import com.sales_record.sales_record.advices.PaginatedResponse;
import com.sales_record.sales_record.dto.SalesDetailDto;
import com.sales_record.sales_record.dto.SalesRecordDto;
import com.sales_record.sales_record.dto.SalesSummaryDto;
import com.sales_record.sales_record.entity.SalesRecord;
import com.sales_record.sales_record.entity.SalesSummary;
import com.sales_record.sales_record.exception.FileProcessingException;
import com.sales_record.sales_record.exception.InvalidFileFormatException;
import com.sales_record.sales_record.repository.SalesRecordRepository;
import com.sales_record.sales_record.repository.SalesSummaryRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService{

    private static final Logger log = LoggerFactory.getLogger(SalesService.class);
    private final SalesSummaryRepository summaryRepository;
    private final SalesRecordRepository recordRepository;
    private final ModelMapper modelMapper;

    public SalesServiceImpl(SalesSummaryRepository summaryRepository, SalesRecordRepository recordRepository, ModelMapper modelMapper) {
        this.summaryRepository = summaryRepository;
        this.recordRepository = recordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"salesSummaries", "salesDetails","salesRecords"}, allEntries = true)
    public SalesSummaryDto processSalesData(MultipartFile file) {
        log.info("Processing sales data from file: {}", file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new InvalidFileFormatException("Uploaded file is empty");
            }

            if (!"text/csv".equals(file.getContentType())) {
                throw new InvalidFileFormatException("Only CSV files are allowed");
            }

            List<CSVRecord> csvRecords;
            try (BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(fileReader,
                         CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
                csvRecords = csvParser.getRecords();
            }

            if (csvRecords.isEmpty()) {
                throw new InvalidFileFormatException("CSV file contains no data");
            }

            List<SalesRecord> salesRecords = new ArrayList<>();
            int totalQuantity = 0;
            double totalRevenue = 0.0;

            for (CSVRecord csvRecord : csvRecords) {
                try {
                    String productName = csvRecord.get("product");
                    int quantity = Integer.parseInt(csvRecord.get("quantity"));
                    double pricePerUnit = Double.parseDouble(csvRecord.get("price"));

                    SalesRecord record = new SalesRecord();
                    record.setProductName(productName);
                    record.setQuantity(quantity);
                    record.setPricePerUnit(pricePerUnit);

                    salesRecords.add(record);

                    totalQuantity += quantity;
                    totalRevenue += quantity * pricePerUnit;
                } catch (Exception e) {
                    log.error("Error processing record: {}", csvRecord, e);
                    throw new FileProcessingException("Error processing record at line " + csvRecord.getRecordNumber());
                }
            }
            SalesSummary summary = new SalesSummary();
            summary.setUploadTimestamp(LocalDateTime.now());
            summary.setTotalRecords(csvRecords.size());
            summary.setTotalQuantity(totalQuantity);
            summary.setTotalRevenue(totalRevenue);
            summary = summaryRepository.save(summary);

            final Long summaryId = summary.getId();
            final LocalDateTime uploadTime = summary.getUploadTimestamp();
            salesRecords.forEach(r -> r.setSummaryId(summaryId));
            salesRecords.forEach(r -> r.setUploadTimestamp(uploadTime));
            recordRepository.saveAll(salesRecords);

            log.info("Successfully processed {} records from file {}", csvRecords.size(), file.getOriginalFilename());

            return modelMapper.map(summary, SalesSummaryDto.class);
        } catch (IOException e) {
            log.error("Error processing file: {}", file.getOriginalFilename(), e);
            throw new FileProcessingException("Error processing file: " + e.getMessage());
        }
    }

    @Override
    @Cacheable(value = "salesSummaries")
    public List<SalesSummaryDto> getAllSummaries() {
        log.info("Fetching all sales summaries");
        try {
            return summaryRepository
                    .findAll()
                    .stream()
                    .map(summary ->
                            modelMapper.map(summary, SalesSummaryDto.class))
                    .toList();
        } catch (Exception e) {
            log.error("Error fetching sales summaries", e);
            throw e;
        }
    }

    @Override
    @Cacheable(value = "salesRecords", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PaginatedResponse<SalesRecordDto> getAllSalesRecords(Pageable pageable) {
        log.info("Fetching paginated sales records - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<SalesRecord> recordsPage = recordRepository.findAll(pageable);

            return PaginatedResponse.fromPage(
                    recordsPage.map(r -> new SalesRecordDto(
                            r.getProductName(),
                            r.getQuantity(),
                            r.getPricePerUnit(),
                            r.getSummaryId(),
                            r.getUploadTimestamp()
                    ))
            );
        } catch (Exception e) {
            log.error("Error fetching sales records", e);
            throw e;
        }
    }

    @Override
    @Cacheable(value = "salesDetails", key = "#summaryId")
    public SalesDetailDto getSalesDetail(Long summaryId) {
        log.info("Fetching sales details for summary ID: {}", summaryId);
        try {
            SalesSummary summary = summaryRepository.findById(summaryId)
                    .orElseThrow(() -> new IllegalArgumentException("Summary not found with ID: " + summaryId));

            List<SalesRecord> records = recordRepository.findBySummaryId(summaryId);

            SalesDetailDto detailDto = modelMapper.map(summary, SalesDetailDto.class);

            List<SalesRecordDto> recordDtos = records.stream()
                    .map(record -> modelMapper.map(record, SalesRecordDto.class))
                    .collect(Collectors.toList());

            detailDto.setRecords(recordDtos);

            return detailDto;
        } catch (Exception e) {
            log.error("Error fetching sales details for summary ID: {}", summaryId, e);
            throw e;
        }
    }
}
