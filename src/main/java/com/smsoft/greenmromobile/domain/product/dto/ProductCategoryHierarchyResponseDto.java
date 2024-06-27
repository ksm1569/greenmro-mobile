package com.smsoft.greenmromobile.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCategoryHierarchyResponseDto {
    private Integer categoryDepth;
    private Long crefItem;
    private String categoryName;
}
