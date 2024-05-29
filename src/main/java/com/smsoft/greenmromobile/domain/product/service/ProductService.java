package com.smsoft.greenmromobile.domain.product.service;

import com.smsoft.greenmromobile.domain.product.dto.*;
import com.smsoft.greenmromobile.domain.product.repository.ProductCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductCustomRepository productCustomRepository;

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductRegListResponseDto> getRegisteredProducts(
        Long userId,
        Long companyId,
        ProductRegListRequestDto productRegListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productRegListRequestDto.page(), productRegListRequestDto.size() + 2);

        return productCustomRepository.getRegisteredProducts(userId, companyId, pageable);
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductUnRegListResponseDto> getUnRegisteredProducts(
            Long userId,
            Long companyId,
            ProductUnRegListRequestDto productUnRegListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productUnRegListRequestDto.page(), productUnRegListRequestDto.size() + 2);

        return productCustomRepository.getUnRegisteredProducts(userId, companyId, pageable);
    }

    @Transactional(readOnly = true)
    public PagedProductResponseDto<ProductPopListResponseDto> getPopProducts(
            ProductPopListRequestDto productPopListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productPopListRequestDto.page(), productPopListRequestDto.size());

        return productCustomRepository.getPopProducts(pageable);
    }

    @Transactional(readOnly = true)
    public ProductDetailResponseDto getProductDetail(Long companyId, Long prefItem) {
        return productCustomRepository.getProductDetail(companyId, prefItem);
    }
}
