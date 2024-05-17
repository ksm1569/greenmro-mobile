package com.smsoft.greenmromobile.domain.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        BigDecimal doseoDelCharge,
        LocalDateTime maxAddedOn,
        LocalDateTime addedOn
) {
}
