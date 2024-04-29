package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BUYERPRICES")
public class BuyerPrice {
    @Id
    @Column(name = "BPLREFITEM", nullable = false)
    private Long bplRefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFITEM")
    private Product product;

    @Column(name = "UNITREFITEM")
    private Long unitRefItem;

    @Column(name = "UNITCODE")
    private String unitCode;

    @Column(name = "BPRICE", nullable = false)
    private BigDecimal bprice;

    @Column(name = "SDATE", nullable = false)
    private String sDate;

    @Column(name = "EDATE", nullable = false)
    private String eDate;

    @Column(name = "MINQTY")
    private BigDecimal minQty;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADDEDON", nullable = false)
    private Date addedOn;

    @Column(name = "UPDATEDBY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATEDON")
    private Date updatedOn;

    @Column(name = "ISINTERFACE")
    private Character isInterface;

    @Column(name = "UCOMPANYREF")
    private Long uCompanyRef;

    @Column(name = "VENDORREFITEM")
    private Long vendorRefItem;

    @Column(name = "PRODUCTTYPE")
    private Character productType;

    @Column(name = "TAXDISPLAY")
    private Character taxDisplay;

    @Column(name = "SOURCINGMD")
    private Long sourcingMd;

    @Column(name = "SPRICE")
    private BigDecimal sprice;

    @Column(name = "DELVFLAG")
    private Character delvFlag;

    @Column(name = "ALLOTFLAG")
    private Character allotFlag;

    @Column(name = "TAX_YN")
    private Character taxYn;

    @Column(name = "PRICEMD")
    private Long priceMd;

    @Column(name = "SUPPLIERPARTNUMBER")
    private String supplierPartNumber;

    @Column(name = "LEADTIME")
    private Integer leadTime;

    @Column(name = "CLINETPARTNUMBER")
    private String clinetPartNumber;

    @Column(name = "POINT_YN", columnDefinition = "CHAR(1) default 'N'")
    private Character pointYn;

    @Temporal(TemporalType.DATE)
    @Column(name = "DEPOTUPDATEDATE")
    private Date depotUpdateDate;
}
