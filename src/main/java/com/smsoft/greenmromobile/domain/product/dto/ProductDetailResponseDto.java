package com.smsoft.greenmromobile.domain.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {
    private Long manufactureId;
    private String manufacturer;
    private String bigImage;
    private String bigImageSub1;
    private String bigImageSub2;
    private String bigImageSub3;
    private String contents;
    private Long prefItem;
    private String pname;
    private String pDescription;
    private Long bplRefItem;
    private BigDecimal bprice;
    private String delChargeYn;
    private BigDecimal delCharge;
    private String doseoDelChargeYn;
    private BigDecimal doseoDelCharge;
    private Long stockQty;
    private BigDecimal minQty;
}