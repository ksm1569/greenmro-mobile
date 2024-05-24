package com.smsoft.greenmromobile.api.product.controller;

import com.smsoft.greenmromobile.domain.product.dto.ProductRegListRequestDto;
import com.smsoft.greenmromobile.domain.product.service.ProductService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductApiController {
    private final JwtUtil jwtUtil;
    private final ProductService productService;

    @GetMapping("/registered")
    public ResponseEntity<?> getRegisteredProducts(
            @CookieValue(name = "accessToken", required = true) String token,
            @Valid @ModelAttribute ProductRegListRequestDto productRegListRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        Long ucompanyRef = jwtUtil.getUcompanyRefFromToken(token);

        return ResponseEntity.ok(productService.getRegisteredProducts(urefItem, ucompanyRef, productRegListRequestDto));
    }

}
