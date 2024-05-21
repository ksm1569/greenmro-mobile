package com.smsoft.greenmromobile.domain.cart.dto;

import jakarta.validation.constraints.Min;

public record CartQtyRequestDto(
        Long ciRefItem,
        Long bplRefItem,

        @Min(value = 1, message = "수량은 1이상만 입력가능합니다")
        Long newQuantity
) {
}
