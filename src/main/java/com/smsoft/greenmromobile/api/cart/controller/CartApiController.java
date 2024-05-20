package com.smsoft.greenmromobile.api.cart.controller;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import com.smsoft.greenmromobile.domain.cart.service.CartService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartApiController {
    private final JwtUtil jwtUtil;
    private final CartService cartService;

    @GetMapping("/cartList")
    public ResponseEntity<PagedCartResponseDto> getCartList(
            @CookieValue(name = "accessToken", required = true) String token,
            @Valid @ModelAttribute CartListRequestDto cartListRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        PagedCartResponseDto pagedResponse = cartService.groupByManufacturer(urefItem, cartListRequestDto);
        return ResponseEntity.ok(pagedResponse);
    }
}
