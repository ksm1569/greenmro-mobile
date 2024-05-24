package com.smsoft.greenmromobile.domain.product.service;

import com.smsoft.greenmromobile.domain.product.dto.PagedProductRegResponseDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListRequestDto;
import com.smsoft.greenmromobile.domain.product.dto.ProductRegListResponseDto;
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
    public PagedProductRegResponseDto<ProductRegListResponseDto> getRegisteredProducts
    (
        Long userId,
        Long companyId,
        ProductRegListRequestDto productRegListRequestDto
    ) {
        Pageable pageable = PageRequest.of(productRegListRequestDto.page(), productRegListRequestDto.size() + 2);

        return productCustomRepository.getRegisteredProducts(userId, companyId, pageable);
    }
}
