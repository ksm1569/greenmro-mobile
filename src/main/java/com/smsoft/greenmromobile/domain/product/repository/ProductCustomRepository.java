package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.dto.*;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    PagedProductResponseDto<ProductsByCategoryResponseDto> getProductsByCategory(Long crefItem, String sort, Pageable pageable);
    PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable);
    PagedProductResponseDto<ProductUnRegListResponseDto> getUnRegisteredProducts(Long userId, Long companyId, Pageable pageable);
    PagedProductResponseDto<ProductNewListResponseDto> getNewProducts(Pageable pageable);
    PagedProductResponseDto<ProductPopListResponseDto> getPopProducts(Pageable pageable);
    ProductDetailResponseDto getProductDetail(Long companyId, Long prefItem);
}
