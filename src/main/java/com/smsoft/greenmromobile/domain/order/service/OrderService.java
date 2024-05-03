package com.smsoft.greenmromobile.domain.order.service;

import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.OrderListResponseDto;
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

    public Map<LocalDate, List<OrderListResponseDto>> groupOrdersByDate(Long userId, OrderListRequestDto orderListRequestDto) {
        Pageable pageable = PageRequest.of(orderListRequestDto.page(), orderListRequestDto.size() + 2);

        List<OrderListResponseDto> orders = orderCustomRepository.findOrderSummariesByUserId(userId, orderListRequestDto, pageable);
        List<OrderListResponseDto> modifiedOrders = orders.stream()
                .map(order -> new OrderListResponseDto(
                        order.soDate(),
                        order.soRefItem(),
                        formatImageUrl(order.bigImage()),
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
                        OrderListResponseDto::getOrderDate,
                        () -> new TreeMap<LocalDate, List<OrderListResponseDto>>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));
    }

    private String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }
}
