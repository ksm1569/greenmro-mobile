package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.dto.PagedProductResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductPopListResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductUnRegListResponseDto;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable);
    PagedProductResponseDto<ProductUnRegListResponseDto> getUnRegisteredProducts(Long userId, Long companyId, Pageable pageable);
    PagedProductResponseDto<ProductPopListResponseDto> getPopProducts(Pageable pageable);
}
