package com.sales_record.sales_record.service;
import com.sales_record.sales_record.advices.PaginatedResponse;
import com.sales_record.sales_record.dto.SalesDetailDto;
import com.sales_record.sales_record.dto.SalesRecordDto;
import com.sales_record.sales_record.dto.SalesSummaryDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SalesService {

    SalesSummaryDto processSalesData(MultipartFile file);

    List<SalesSummaryDto> getAllSummaries();

    PaginatedResponse<SalesRecordDto> getAllSalesRecords(Pageable pageable);

    SalesDetailDto getSalesDetail(Long summaryId);
}
