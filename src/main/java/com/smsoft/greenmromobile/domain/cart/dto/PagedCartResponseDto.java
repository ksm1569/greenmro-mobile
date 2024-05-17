package com.smsoft.greenmromobile.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PagedCartResponseDto {
    private Map<String, List<CartListResponseDto>> groupedOrders;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
