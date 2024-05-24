package com.smsoft.greenmromobile.domain.product.repository;

import com.smsoft.greenmromobile.domain.product.dto.PagedProductRegResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListResponseDto;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    PagedProductRegResponseDto<ProductRegListResponseDto> getRegisteredProducts(Long userId, Long companyId, Pageable pageable);
}
