package com.sales_record.sales_record.repository;

import com.sales_record.sales_record.entity.SalesRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRecordRepository extends JpaRepository<SalesRecord, Long> {
    List<SalesRecord> findBySummaryId(Long summaryId);
    Page<SalesRecord> findAll(Pageable pageable);
}
