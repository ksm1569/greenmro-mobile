package com.smsoft.greenmromobile.api.product.controller;

import com.smsoft.greenmromobile.domain.product.dto.*;
import com.smsoft.greenmromobile.domain.product.service.ProductService;
import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductApiController {
    private final JwtUtil jwtUtil;
    private final ProductService productService;

    @GetMapping("/category")
    public ResponseEntity<?> getProductByCategory(@Valid @ModelAttribute ProductsByCategoryRequestDto productsByCategoryRequestDto) {
        return ResponseEntity.ok(productService.getProductsByCategory(productsByCategoryRequestDto));
    }

    @GetMapping("/related-search")
    public ResponseEntity<?> productRelatedList(
            @CookieValue(name = "accessToken", required = true) String token,
            @RequestParam String productName,
            @RequestParam String prefItem,
            @RequestParam Integer size,
            @RequestParam Integer page,
            @RequestParam(required = false) String sort
    ) throws IOException {
        Long ucompanyRef = jwtUtil.getUcompanyRefFromToken(token);
        return ResponseEntity.ok(productService.productRelatedList(ucompanyRef, "products_search", productName, prefItem, size, page, sort));
    }

    @GetMapping("/search")
    public ResponseEntity<?> productSearch(@RequestParam String productName, @RequestParam Integer size) throws IOException {
        return ResponseEntity.ok(productService.productSearch("products_search", productName, size));
    }

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

    @GetMapping("/new-product")
    public ResponseEntity<?> getNewProducts(
            @Valid @ModelAttribute ProductNewRequestDto productNewRequestDto
    ) {
        return ResponseEntity.ok(productService.getNewProducts(productNewRequestDto));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopProducts(
            @Valid @ModelAttribute ProductPopListRequestDto productPopListRequestDto
    ) {
        return ResponseEntity.ok(productService.getPopProducts(productPopListRequestDto));
    }

    @GetMapping("/{prefItem}")
    public ResponseEntity<?> getProductDetail(
            @CookieValue(name = "accessToken", required = true) String token,
            @PathVariable Long prefItem
    ) {
        Long ucompanyRef = jwtUtil.getUcompanyRefFromToken(token);
        return ResponseEntity.ok(productService.getProductDetail(ucompanyRef, prefItem));
    }

}
