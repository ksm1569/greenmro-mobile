package com.smsoft.greenmromobile.domain.product.dto;

import java.util.List;

public record PagedProductResponseDto<T>(
        List<T> content,
        long currentPageNumber,
        long totalElements,
        int totalPages,
        boolean last
) {
}
