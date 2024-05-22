package com.smsoft.greenmromobile.api.cart.controller;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartQtyRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import com.smsoft.greenmromobile.domain.cart.service.CartService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/changeQty")
    public ResponseEntity<?> changeQty(
            @CookieValue(name = "accessToken", required = true) String token,
            @RequestBody @Valid CartQtyRequestDto cartQtyRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        cartService.changeQty(urefItem, cartQtyRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ciRefItem}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long ciRefItem) {
        cartService.deleteCartItem(ciRefItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteMultiple")
    public ResponseEntity<?> deleteMultipleItems(@RequestBody List<Long> ids) {
        cartService.deleteMultipleItems(ids);
        return ResponseEntity.ok().build();
    }
}
