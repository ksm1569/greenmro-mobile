package com.smsoft.greenmromobile.domain.cart.repository;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartCustomRepositoryImplTest {

    @Autowired
    private CartCustomRepository cartCustomRepository;

    @DisplayName("카트 데이터 찍어보기")
    @Test
    public void testFindCartInfoByUserId() {
        Long userId = 104760L;
        CartListRequestDto requestDto = new CartListRequestDto(0, 5);
        Pageable pageable = PageRequest.of(0, 5);

        PagedCartResponseDto result = cartCustomRepository.findCartInfoByUserId(userId, requestDto, pageable);

        System.out.println("result = " + result.toString());
    }


}