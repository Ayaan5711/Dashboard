package com.sales_record.sales_record.repository;

import com.sales_record.sales_record.entity.SalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {
}
