package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartListResponseDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CartCustomRepository {
    PagedCartResponseDto findCartInfoByUserId(Long userId, CartListRequestDto cartListRequestDto);
}
