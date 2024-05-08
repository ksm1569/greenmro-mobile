package com.smsoft.greenmromobile.domain.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderListResponseDto(
        LocalDateTime soDate,          // 주문일자
        String soRefItem,              // 주문번호
        Long orefItem,
        String bigImage,               // 상품이미지
        Long prefItem,                 // 상품코드
        String pname,                  // 상품명
        String pDescription,           // 규격
        BigDecimal obuyPrice,          // 상품단가
        BigDecimal salesSplAmt,        // 판매금액
        BigDecimal salesTaxAmt,        // 판매부가세
        BigDecimal delivery,           // 배송비
        BigDecimal oqty,               // 수량
        String delFlag                 // 주문취소여부
) {
    public LocalDate getOrderDate() {
        return soDate.toLocalDate();
    }
}
