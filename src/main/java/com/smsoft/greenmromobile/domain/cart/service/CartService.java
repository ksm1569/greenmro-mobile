package com.smsoft.greenmromobile.domain.cart.service;

import com.smsoft.greenmromobile.domain.cart.dto.CartListRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.CartQtyRequestDto;
import com.smsoft.greenmromobile.domain.cart.dto.PagedCartResponseDto;
import com.smsoft.greenmromobile.domain.cart.entity.Cart;
import com.smsoft.greenmromobile.domain.cart.repository.CartCustomRepository;
import com.smsoft.greenmromobile.domain.cart.repository.CartRepository;
import com.smsoft.greenmromobile.global.error.ErrorCode;
import com.smsoft.greenmromobile.global.error.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartCustomRepository cartCustomRepository;

    @Transactional(readOnly = true)
    public PagedCartResponseDto groupByManufacturer(Long userId, CartListRequestDto cartListRequestDto) {
        return cartCustomRepository.findCartInfoByUserId(userId, cartListRequestDto);
    }

    @Transactional
    public void changeQty(Long userId, CartQtyRequestDto cartQtyRequestDto) {
        // 정상적인 수량 값 확인
        if (cartQtyRequestDto.newQuantity() < 1) {
            throw new BusinessException(ErrorCode.INVALID_QUANTITY);
        }
        
        Cart cart = cartRepository.findCartByCustomQuery(cartQtyRequestDto.ciRefItem(), cartQtyRequestDto.bplRefItem(), userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        // 벨리데이션 체크
        validateBusinessConditions(cart, cartQtyRequestDto.newQuantity());

        cart.changeQty(BigDecimal.valueOf(cartQtyRequestDto.newQuantity()));
        cartRepository.save(cart);
    }

    private void validateBusinessConditions(Cart cart, Long newQuantity) {
        // 입력 수량이 재고수량을 초과했는지 체크
        Long stockQty = cart.getProduct().getStockQty();
        if (newQuantity > stockQty) {
            throw new BusinessException(ErrorCode.EXCEEDED_QUANTITY);
        }
    }
}
