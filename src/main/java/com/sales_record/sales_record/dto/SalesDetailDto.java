package com.sales_record.sales_record.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SalesDetailDto {
    private Long summaryId;
    private LocalDateTime uploadTimestamp;
    private Integer totalRecords;
    private Integer totalQuantity;
    private Double totalRevenue;
    private List<SalesRecordDto> records;

    public SalesDetailDto() {
    }

    public SalesDetailDto(Long summaryId, LocalDateTime uploadTimestamp, Integer totalRecords, Integer totalQuantity, Double totalRevenue, List<SalesRecordDto> records) {
        this.summaryId = summaryId;
        this.uploadTimestamp = uploadTimestamp;
        this.totalRecords = totalRecords;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
        this.records = records;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    public LocalDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(LocalDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<SalesRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<SalesRecordDto> records) {
        this.records = records;
    }
}
