package com.sales_record.sales_record.advices;

import org.springframework.data.domain.Page;

import java.util.List;


public class PaginatedResponse<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    public PaginatedResponse() {
    }

    public PaginatedResponse(List<T> content, int currentPage, int totalPages, long totalItems, int pageSize) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
    }

    public static <T> PaginatedResponse<T> fromPage(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }
}
