package com.smsoft.greenmromobile.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElasticProductResponseDto {
    private Integer crefitem;
    private Integer prefitem;
    private String pname;
    private String ptechdescription;
    private String bigImage;
    private Integer price;
}
