package com.smsoft.greenmromobile.api.order.controller;

import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.OrderListResponseDto;
import com.smsoft.greenmromobile.domain.order.service.OrderService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
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
    public ResponseEntity<Map<LocalDate, List<OrderListResponseDto>>> getOrderList(
            @CookieValue(name = "accessToken", required = false) String token,
            @Valid @ModelAttribute OrderListRequestDto orderListRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        Map<LocalDate, List<OrderListResponseDto>> groupedOrders = orderService.groupOrdersByDate(urefItem, orderListRequestDto);
        return ResponseEntity.ok(groupedOrders);
    }
}
