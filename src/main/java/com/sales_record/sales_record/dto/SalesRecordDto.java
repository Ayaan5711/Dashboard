package com.sales_record.sales_record.dto;

import java.time.LocalDateTime;

public class SalesRecordDto {
    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
    private Long summaryId;
    private LocalDateTime uploadTimestamp;

    public SalesRecordDto() {
    }

    public SalesRecordDto(String productName, Integer quantity, Double pricePerUnit, Long summaryId, LocalDateTime uploadTimestamp) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.summaryId = summaryId;
        this.uploadTimestamp = uploadTimestamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
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
}
