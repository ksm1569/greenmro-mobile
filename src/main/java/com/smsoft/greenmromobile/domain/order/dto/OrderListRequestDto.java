package com.smsoft.greenmromobile.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public record OrderListRequestDto(
        @Size(max = 100, message = "상품명은 100자까지 입력가능합니다")
        String productName,
        @NotNull(message = "조회 시작일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @NotNull(message = "조회 종료일은 필수입니다.")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate,

        Integer page,

        Integer size
) {
    public OrderListRequestDto(
            String productName,
            LocalDate startDate,
            LocalDate endDate,
            Integer page,
            Integer size
    ) {
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = Objects.requireNonNullElse(page, 0);
        this.size = Objects.requireNonNullElse(size, 3);
    }
}
