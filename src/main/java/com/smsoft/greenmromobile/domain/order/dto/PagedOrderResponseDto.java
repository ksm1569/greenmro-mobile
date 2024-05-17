package com.smsoft.greenmromobile.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PagedOrderResponseDto {
    private Map<LocalDate, List<OrderListResponseDto>> groupedOrders;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
