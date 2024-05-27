package com.smsoft.greenmromobile.domain.order.service;

import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.OrderListResponseDto;
import com.smsoft.greenmromobile.domain.order.dto.PagedOrderResponseDto;
import com.smsoft.greenmromobile.domain.order.repository.OrderCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public PagedOrderResponseDto groupOrdersByDate(Long userId, OrderListRequestDto orderListRequestDto) {
        Pageable pageable = PageRequest.of(orderListRequestDto.page(), orderListRequestDto.size() + 2);

        List<OrderListResponseDto> orders = orderCustomRepository.findOrderSummariesByUserId(userId, orderListRequestDto, pageable);
        List<OrderListResponseDto> modifiedOrders = orders.stream()
                .map(order -> new OrderListResponseDto(
                        order.soDate(),
                        order.soRefItem(),
                        order.orefItem(),
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

        long totalElements = orderCustomRepository.countOrdersByUserIdAndFilters(userId, orderListRequestDto);
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize()) - 1;
        boolean last = pageable.getPageNumber() >= totalPages - 1;

        Map<LocalDate, List<OrderListResponseDto>> groupedOrders = modifiedOrders.stream()
                .collect(Collectors.groupingBy(
                        OrderListResponseDto::getOrderDate,
                        () -> new TreeMap<LocalDate, List<OrderListResponseDto>>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));

        return new PagedOrderResponseDto(groupedOrders, totalElements, totalPages, last);
    }

    private String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://") && !imageUrl.contains("http://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }
}
