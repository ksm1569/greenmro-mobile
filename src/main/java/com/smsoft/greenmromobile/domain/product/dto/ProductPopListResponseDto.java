package com.smsoft.greenmromobile.domain.product.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPopListResponseDto {
    private Long prefItem;
    private String bigImage;
    private String pname;
    private java.math.BigDecimal bprice;
    private Long cnt;
    private Long ranking;
}
