package com.smsoft.greenmromobile.api.order.controller;

import com.smsoft.greenmromobile.domain.order.dto.OrderListRequestDto;
import com.smsoft.greenmromobile.domain.order.dto.PagedOrderResponse;
import com.smsoft.greenmromobile.domain.order.service.OrderService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderApiController {
    private final JwtUtil jwtUtil;
    private final OrderService orderService;

    @GetMapping("/orderList")
    public ResponseEntity<PagedOrderResponse> getOrderList(
            @CookieValue(name = "accessToken", required = true) String token,
            @Valid @ModelAttribute OrderListRequestDto orderListRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        PagedOrderResponse pagedResponse = orderService.groupOrdersByDate(urefItem, orderListRequestDto);
        return ResponseEntity.ok(pagedResponse);
    }
}
