package com.smsoft.greenmromobile.domain.product.dto;

import java.util.List;

public record PagedProductRegResponseDto<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        boolean last
) {
}
