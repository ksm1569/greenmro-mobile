package com.smsoft.greenmromobile.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElasticProductSearchResponseDto {
    private List<ElasticProductResponseDto> products;
    private int currentPage;
    private int totalItems;
    private int totalPages;
}
