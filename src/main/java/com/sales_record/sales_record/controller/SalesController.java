package com.sales_record.sales_record.controller;

import com.sales_record.sales_record.advices.ApiResponse;
import com.sales_record.sales_record.advices.PaginatedResponse;
import com.sales_record.sales_record.dto.SalesDetailDto;
import com.sales_record.sales_record.dto.SalesRecordDto;
import com.sales_record.sales_record.dto.SalesSummaryDto;
import com.sales_record.sales_record.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/v1/sales")
@CrossOrigin("*")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }
    private static final Logger log = LoggerFactory.getLogger(SalesController.class);
    @PostMapping("/upload-sales-data")
    public ResponseEntity<ApiResponse<SalesSummaryDto>> uploadSalesData(@RequestParam("file") MultipartFile file) {
        log.info("Received request to upload sales data");
        try {
            SalesSummaryDto summary = salesService.processSalesData(file);
            return ResponseEntity.ok(ApiResponse.success(summary));
        } catch (Exception e) {
            log.error("Error processing file upload", e);
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
        }
    }

    @GetMapping("/sales-summaries")
    public ResponseEntity<ApiResponse<List<SalesSummaryDto>>> getAllSalesSummaries() {
        log.info("Received request for all sales summaries");
        try {
            List<SalesSummaryDto> summaries = salesService.getAllSummaries();
            return ResponseEntity.ok(ApiResponse.success(summaries));
        } catch (Exception e) {
            log.error("Error fetching sales summaries", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error fetching sales summaries", e.getClass().getSimpleName()));
        }
    }

    @GetMapping("/sales-summaries/{id}")
    public ResponseEntity<ApiResponse<SalesDetailDto>> getSalesDetail(@PathVariable Long id) {
        log.info("Received request for sales detail with ID: {}", id);
        try {
            SalesDetailDto detail = salesService.getSalesDetail(id);
            return ResponseEntity.ok(ApiResponse.success(detail));
        } catch (Exception e) {
            log.error("Error fetching sales detail for ID: {}", id, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage(), e.getClass().getSimpleName()));
        }
    }

    @GetMapping("/sales-records")
    public ResponseEntity<ApiResponse<PaginatedResponse<SalesRecordDto>>> getAllSalesRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Received request for paginated sales records - page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            PaginatedResponse<SalesRecordDto> response = salesService.getAllSalesRecords(pageable);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("Error fetching paginated sales records", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error fetching sales records", e.getClass().getSimpleName()));
        }
    }
}
