package com.smsoft.greenmromobile.domain.order.repository;

import com.smsoft.greenmromobile.domain.order.dto.OrderSummaryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderCustomRepository {
    List<OrderSummaryDto> findOrderSummariesByUserId(Long userId, Pageable pageable);
}
