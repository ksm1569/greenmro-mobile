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
@Table(name = "CATEGORYRELATION",
        indexes = {
                @Index(name = "CATEGORY_PARENT_RELATION_REF", columnList = "CPARENTREF"),
                @Index(name = "IDX_CATEGORYRELATION01", columnList = "CREFITEM, CPARENTREF"),
                @Index(name = "IDX_CATEGORYRELATION_CREFITEM", columnList = "CREFITEM"),
                @Index(name = "IDX_CATEGORYRELATION_HIERARCHY", columnList = "CPARENTREF, CREFITEM")
        })
public class CategoryRelation {

    @Id
    @Column(name = "CRELATIONREF", nullable = false)
    private Long cRelationRef;

    @Column(name = "CPARENTREF", nullable = false)
    private Long cParentRef;

    @Column(name = "HASCHILDREN", columnDefinition = "NUMBER(1) default 0")
    private Integer hasChildren;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREFITEM", nullable = false)
    private Category category;
}