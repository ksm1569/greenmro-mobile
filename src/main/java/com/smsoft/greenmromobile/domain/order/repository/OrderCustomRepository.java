package com.smsoft.greenmromobile.domain.order.repository;

import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.OrderListResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderCustomRepository {
    List<OrderListResponseDto> findOrderSummariesByUserId(Long userId, OrderListRequestDto orderListRequestDto, Pageable pageable);
    long countOrdersByUserIdAndFilters(Long userId, OrderListRequestDto orderListRequestDto);
}
