package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTCATEGORIES")
public class ProductCategory {
    @Id
    @Column(name = "PCREFITEM", nullable = false)
    private Long pcRefItem;

    @Column(name = "PREFITEM", nullable = false)
    private Long pRefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREFITEM")
    private Category category;

    @Column(name = "USE_YN", columnDefinition = "CHAR(1) default 'Y'")
    private String useYn;
}

