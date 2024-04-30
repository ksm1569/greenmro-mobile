package com.smsoft.greenmromobile.domain.order.service;

import com.smsoft.greenmromobile.domain.order.dto.OrderSummaryDto;
import com.smsoft.greenmromobile.domain.order.repository.OrderCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderCustomRepository orderCustomRepository;

    public Map<LocalDate, List<OrderSummaryDto>> groupOrdersByDate(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<OrderSummaryDto> orders = orderCustomRepository.findOrderSummariesByUserId(userId, pageable);
        List<OrderSummaryDto> modifiedOrders = orders.stream()
                .map(order -> new OrderSummaryDto(
                        order.soDate(),
                        order.soRefItem(),
                        order.bigImage() != null ? "https://shop.greenproduct.co.kr" + order.bigImage() : null,
                        order.prefItem(),
                        order.pname(),
                        order.pDescription(),
                        order.obuyPrice(),
                        order.salesSplAmt(),
                        order.salesTaxAmt(),
                        order.delivery(),
                        order.oqty(),
                        order.delFlag()
                ))
                .toList();

        return modifiedOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.soDate().toLocalDate(),
                        () -> new TreeMap<LocalDate, List<OrderSummaryDto>>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));

// 추후 이미지서버 이전시 주석해제후 적용
//        return orderCustomRepository.findOrderSummariesByUserId(userId)
//                .stream()
//                .collect(Collectors.groupingBy(orders -> orders.soDate().toLocalDate(),
//                        () -> new TreeMap<LocalDate, List<OrderSummaryDto>>(Comparator.reverseOrder()),
//                        Collectors.toList()
//                ));
    }
}
