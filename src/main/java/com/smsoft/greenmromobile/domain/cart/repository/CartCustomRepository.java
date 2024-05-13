package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartListResponseDto;

import java.awt.print.Pageable;
import java.util.List;

public interface CartCustomRepository {
    List<CartListResponseDto> findCartInfoByUserId(Long userId, CartListRequestDto cartListRequestDto, Pageable pageable);
}
