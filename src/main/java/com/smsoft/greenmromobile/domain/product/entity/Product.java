package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @Column(name = "PREFITEM", nullable = false)
    private Long prefItem;

    @Column(name = "PARTNUMBER", nullable = false)
    private String partNumber;

    @Column(name = "PADDEDBY")
    private Long paddedBy;

    @Column(name = "PADDEDON", nullable = false)
    private LocalDateTime paddedOn;

    @Column(name = "PMANUFACTURER")
    private String manufacturer;

    @Column(name = "PNAME", nullable = false)
    private String pname;

    @Column(name = "PDESCRIPTION")
    private String description;

    @Column(name = "PTECHDESCRIPTION")
    private String techDescription;

    @Column(name = "PTYREFITEM")
    private Long ptyRefItem;

    @Column(name = "LEADTIME")
    private Integer leadTime;

    @Column(name = "PACKINGDESCRIPTION")
    private String packingDescription;

    @Column(name = "MODELNAME")
    private String modelName;

    @Column(name = "BRANDNAME")
    private String brandName;

    @Column(name = "UNITREFITEM")
    private Long unitRefItem;

    @Column(name = "ISUSE", columnDefinition = "CHAR(1) default 'Y'")
    private String isUse;

    @Column(name = "FILENAME")
    private String fileName;

    @Column(name = "ECOLABEL", columnDefinition = "CHAR(7) default 'N'")
    private String ecoLabel;

    @Column(name = "RECYCLING", columnDefinition = "CHAR(1) default 'N'")
    private String recycling;

    @Column(name = "TAX_YN", columnDefinition = "CHAR(1) default 'Y'")
    private String taxYn;

    @Column(name = "ORIGIN")
    private String origin;

    @Column(name = "BIGIMAGE")
    private String bigImage;

    @Column(name = "SMALLIMAGE")
    private String smallImage;

    @Column(name = "UPDATEDBY")
    private Long updatedBy;

    @Column(name = "UPDATEDON")
    private LocalDateTime updatedOn;

    @Column(name = "PMANUFACTUREID")
    private Long manufactureId;

    @Column(name = "OPTIONTYPE")
    private String optionType;

    @Column(name = "STOCKQTY")
    private Long stockQty;

    @Column(name = "BIGIMAGESUB1")
    private String bigImageSub1;

    @Column(name = "BIGIMAGESUB2")
    private String bigImageSub2;

    @Column(name = "BIGIMAGESUB3")
    private String bigImageSub3;

    @Column(name = "VENDORREFITEM")
    private Long vendorRefItem;

    @Column(name = "DELCHARGEFREEQTY")
    private Long delChargeFreeQty;

    @Column(name = "LOT_YN", columnDefinition = "CHAR(1) default 'N'")
    private String lotYn;

    @Column(name = "LOT_QTY")
    private Long lotQty;

    @Column(name = "LOT_LEADTIME")
    private Integer lotLeadTime;

    @Column(name = "LOT_DAILYUNIT", columnDefinition = "CHAR(1)")
    private String lotDailyUnit;

    @Column(name = "SIZEWEIGHT")
    private String sizeWeight;

    @Column(name = "MATERIAL")
    private String material;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "BOXQTY")
    private String boxQty;

    @Column(name = "CARTOONQTY")
    private String cartoonQty;

    @Column(name = "REFUSECOMMENT")
    private String refuseComment;

    @Column(name = "TOT_SALSE_CNT")
    private Long totalSalesCnt;

    @Column(name = "SPRICE")
    private BigDecimal sprice;

    @Column(name = "IFCD")
    private Long ifcd;

    @Column(name = "BUYCOMP")
    private String buyComp;

    @Column(name = "VENDORITEMCD")
    private String vendorItemCd;

    @Column(name = "PRODELCD")
    private String prodElCd;

    @Column(name = "PRODG2BD")
    private String prodG2Bd;

    @Column(name = "PRODPRCE")
    private String prodPrice;

    @Column(name = "PRODECYN", columnDefinition = "CHAR(1) default 'N'")
    private String prodEcYn;

    @Column(name = "PRODGRYN", columnDefinition = "CHAR(1) default 'N'")
    private String prodGrYn;

    @Column(name = "PRODGRID")
    private String prodGrId;

    @Column(name = "PRODGSDT")
    private String prodGsDt;

    @Column(name = "PRODGEDT")
    private String prodGeDt;

    @Column(name = "ECOPROYN", columnDefinition = "CHAR(1) default 'N'")
    private String ecoProYn;

    @Column(name = "MAJORHANDICAPYN", columnDefinition = "CHAR(1) default 'N'")
    private String majorHandicapYn;

    @Column(name = "SOCIALYN", columnDefinition = "CHAR(1) default 'N'")
    private String socialYn;

    @Column(name = "WOMANYN", columnDefinition = "CHAR(1) default 'N'")
    private String womanYn;

    @Column(name = "SOCCOPYN", columnDefinition = "CHAR(1) default 'N'")
    private String soccoPyN;

    @Column(name = "HANDICAPSTANDARDYN", columnDefinition = "CHAR(1) default 'N'")
    private String handicapStandardYn;

    @Column(name = "HANDICAPYN", columnDefinition = "CHAR(1) default 'N'")
    private String handicapYn;

    @Column(name = "GREENPROYN", columnDefinition = "CHAR(1) default 'N'")
    private String greenProYn;

    @Column(name = "PRODRSID")
    private String prodRsId;

    @Column(name = "PRODTECYN", columnDefinition = "CHAR(1) default 'N'")
    private String prodTecYn;

    @Column(name = "VETERANSYN", columnDefinition = "CHAR(1) default 'N'")
    private String veteranSyn;

    @Column(name = "SAFETY_PRODUCT_VOLUME_NO")
    private String safetyProductVolumeNo;

    @Column(name = "NEW_TECH_YN", columnDefinition = "CHAR")
    private String newTechYn;

    @Column(name = "MSDS_YN", columnDefinition = "CHAR")
    private String msdsYn;

    @Column(name = "MSDS_PDF")
    private String msdsPdf;

    @Column(name = "ASSETYN", columnDefinition = "CHAR")
    private String assetYn;

    @Column(name = "COMPANY_VISIT_LINK")
    private String companyVisitLink;

    @Column(name = "MAXQTY")
    private Long maxQty;

    @Column(name = "LOWCARBONYN", columnDefinition = "CHAR(1) default 'N'")
    private String lowCarbonYn;

    @Column(name = "GRADE")
    private Integer grade;

    @Column(name = "ISUSE_2", columnDefinition = "CHAR")
    private String isUse2;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFITEM", referencedColumnName = "PREFITEM")
    private ProductContent productContent;
}
