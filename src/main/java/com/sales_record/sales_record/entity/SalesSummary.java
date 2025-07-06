package com.sales_record.sales_record.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity

public class SalesSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime uploadTimestamp;
    private Integer totalRecords;
    private Integer totalQuantity;
    private Double totalRevenue;

    public SalesSummary() {
    }

    public SalesSummary(Long id, LocalDateTime uploadTimestamp, Integer totalRecords, Integer totalQuantity, Double totalRevenue) {
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
