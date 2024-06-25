package com.smsoft.greenmromobile.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNewListResponseDto {
    private Long prefItem;
    private String bigImage;
    private String pname;
    private java.math.BigDecimal bprice;
}