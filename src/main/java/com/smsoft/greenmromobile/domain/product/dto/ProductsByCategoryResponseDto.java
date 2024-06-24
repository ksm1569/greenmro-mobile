package com.smsoft.greenmromobile.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductsByCategoryResponseDto {
    private Long crefItem;
    private String categoryNm;
    private Long manufactureId;
    private String manufacturer;
    private Long prefItem;
    private String bigImage;
    private String pname;
    private String pDescription;
    private java.math.BigDecimal bprice;
}
