package com.smsoft.greenmromobile.api.order.controller;

import com.smsoft.greenmromobile.domain.order.dto.OrderSummaryDto;
import com.smsoft.greenmromobile.domain.order.service.OrderService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderApiController {
    private final JwtUtil jwtUtil;
    private final OrderService orderService;

    @GetMapping("/orderList")
    public ResponseEntity<Map<LocalDate, List<OrderSummaryDto>>> getOrderList(
            @CookieValue(name = "accessToken", required = false) String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        Map<LocalDate, List<OrderSummaryDto>> groupedOrders = orderService.groupOrdersByDate(urefItem, page, size);
        return ResponseEntity.ok(groupedOrders);
    }
}
