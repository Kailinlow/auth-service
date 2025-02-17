package com.kltn.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
