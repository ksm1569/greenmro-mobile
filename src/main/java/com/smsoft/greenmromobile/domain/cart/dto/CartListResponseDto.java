package com.smsoft.greenmromobile.domain.cart.dto;

import java.math.BigDecimal;

public record CartListResponseDto(
        Long manufactureId,
        String manufacturer,
        String pname,
        String bigImage,
        BigDecimal oQty,
        BigDecimal bprice,
        String delChargeYn,
        BigDecimal delCharge,
        String doseoDelChargeYn,
        BigDecimal doseoDelCharge
) {
}
