package com.smsoft.greenmromobile.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record OrderListRequestDto(
        @Size(max = 100, message = "상품명은 100자까지 입력가능합니다")
        String productName,
        @NotNull(message = "조회 시작일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @NotNull(message = "조회 종료일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,
        int page,
        int size
) {
}
