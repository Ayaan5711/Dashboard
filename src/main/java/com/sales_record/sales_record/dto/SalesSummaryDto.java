package com.sales_record.sales_record.dto;

import java.time.LocalDateTime;


public class SalesSummaryDto {
    private Long id;
    private LocalDateTime uploadTimestamp;
    private Integer totalRecords;
    private Integer totalQuantity;
    private Double totalRevenue;

    public SalesSummaryDto() {
    }

    public SalesSummaryDto(Long id, LocalDateTime uploadTimestamp, Integer totalRecords, Integer totalQuantity, Double totalRevenue) {
        this.id = id;
        this.uploadTimestamp = uploadTimestamp;
        this.totalRecords = totalRecords;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
