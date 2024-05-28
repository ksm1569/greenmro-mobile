package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOP_3000_POPULAR_PRODUCTS")
public class PopularProduct {
    @Id
    @Column(name = "PREFITEM", nullable = false)
    private Long prefItem;

    @Column(name = "BIGIMAGE")
    private String bigImage;

    @Column(name = "PNAME")
    private String pname;

    @Column(name = "BPRICE")
    private BigDecimal bprice;

    @Column(name = "CNT")
    private Long cnt;

    @Column(name = "RANKING")
    private Long ranking;
}
