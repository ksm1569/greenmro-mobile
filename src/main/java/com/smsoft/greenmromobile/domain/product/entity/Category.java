package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @Column(name = "CREFITEM", nullable = false)
    private Long cRefItem;

    @Column(name = "CATEGORY", nullable = false, length = 100)
    private String category;

    @Column(name = "CATEGORYCODE", length = 10)
    private String categoryCode;

    @Column(name = "CATEGORYDEPTH", nullable = false, columnDefinition = "NUMBER(1) default 1")
    private Integer categoryDepth;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Column(name = "ADDEDON", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDateTime addedOn;

    @Column(name = "UPDATEDBY")
    private Long updatedBy;

    @Column(name = "UPDATEDON")
    private LocalDateTime updatedOn;

    @Column(name = "ISUSE", columnDefinition = "CHAR(1) default 'Y'")
    private String isUse;

    @Column(name = "GRADE", columnDefinition = "NUMBER(2) default 0")
    private Integer grade;
}
