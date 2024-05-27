package com.smsoft.greenmromobile.api.product.controller;

import com.smsoft.greenmromobile.domain.product.dto.ProductRegListRequestDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductUnRegListRequestDto;
import com.smsoft.greenmromobile.domain.product.service.ProductService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
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

    @GetMapping("/unregistered")
    public ResponseEntity<?> getUnRegisteredProducts(
            @CookieValue(name = "accessToken", required = true) String token,
            @Valid @ModelAttribute ProductUnRegListRequestDto productUnRegListRequestDto
    ) {
        Long urefItem = jwtUtil.getUrefItemFromToken(token);
        Long ucompanyRef = jwtUtil.getUcompanyRefFromToken(token);

        return ResponseEntity.ok(productService.getUnRegisteredProducts(urefItem, ucompanyRef, productUnRegListRequestDto));
    }

}
