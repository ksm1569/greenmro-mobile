package com.smsoft.greenmromobile.domain.cart.service;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartListResponseDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import com.smsoft.greenmromobile.domain.cart.repository.CartCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartCustomRepository cartCustomRepository;

    @Transactional(readOnly = true)
    public PagedCartResponseDto groupByManufacturer(Long userId, CartListRequestDto cartListRequestDto) {
        Pageable pageable = PageRequest.of(cartListRequestDto.page(), cartListRequestDto.size() + 2);

        return cartCustomRepository.findCartInfoByUserId(userId, cartListRequestDto, pageable);
    }

    private String formatImageUrl(String imageUrl) {
        if (imageUrl != null && !imageUrl.contains("https://")) {
            return "https://shop.greenproduct.co.kr" + imageUrl;
        }
        return imageUrl;
    }

}
