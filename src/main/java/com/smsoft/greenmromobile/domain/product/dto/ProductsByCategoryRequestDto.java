package com.smsoft.greenmromobile.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsByCategoryRequestDto {
    private Long categoryId;
    private String sort;
    private Integer page;
    private Integer size;
}
