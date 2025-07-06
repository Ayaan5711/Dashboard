package com.sales_record.sales_record.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

@Table(name = "sales_record", indexes = {
    @Index(name = "idx_summary_id", columnList = "summaryId"),
    @Index(name = "idx_product_name", columnList = "productName")
})
public class SalesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
    private LocalDateTime uploadTimestamp;
    private Long summaryId;

    public SalesRecord() {
    }

    public SalesRecord(Long id, String productName, Integer quantity, Double pricePerUnit, LocalDateTime uploadTimestamp, Long summaryId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.uploadTimestamp = uploadTimestamp;
        this.summaryId = summaryId;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUploadTimestamp() {
        return uploadTimestamp;
    }

    public void setUploadTimestamp(LocalDateTime uploadTimestamp) {
        this.uploadTimestamp = uploadTimestamp;
    }

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }
}

