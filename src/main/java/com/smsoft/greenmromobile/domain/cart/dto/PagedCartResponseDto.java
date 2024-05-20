package com.smsoft.greenmromobile.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class PagedCartResponseDto {
    private Map<String, List<CartListResponseDto>> groupedOrders;
    private long totalElements;
}
