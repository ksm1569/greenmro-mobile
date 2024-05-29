package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTCONTENTS")
public class ProductContent {
    @Id
    @Column(name = "PREFITEM", nullable = false)
    private Long prefItem;

    @Lob
    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Column(name = "ADDEDON")
    private LocalDateTime addedOn;

    @Column(name = "UPDATEDBY")
    private Long updatedBy;

    @Column(name = "UPDATEDON")
    private LocalDateTime updatedOn;
}
