package com.smsoft.greenmromobile.domain.order.entity;

import com.smsoft.greenmromobile.domain.product.entity.BuyerPrice;
import com.smsoft.greenmromobile.domain.product.entity.Product;
import com.smsoft.greenmromobile.domain.user.entity.UserRelation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDERITEMS")
public class OrderItems {
    @Id
    @Column(name = "OREFITEM", nullable = false)
    private Long orefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOREFITEM", nullable = false)
    private SalesOrder salesOrder;

    @Column(name = "PARTNUMBER")
    private String partNumber;

    @Column(name = "PNAME", nullable = false)
    private String pname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFITEM", nullable = false)
    private Product product;

    @Column(name = "IVENDPARTNUMBER", columnDefinition = "VARCHAR2(75) default 'N/A'")
    private String ivendPartNumber;

    @Column(name = "OBUYPRICE", nullable = false)
    private BigDecimal obuyPrice;

    @Column(name = "OSELLPRICE", nullable = false)
    private BigDecimal osellPrice;

    @Column(name = "OQTY", nullable = false)
    private BigDecimal oqty;

    @Column(name = "OSTATUS", nullable = false)
    private Integer ostatus;

    @Column(name = "OSUBSTATUS", nullable = false)
    private Integer osubStatus;

    @Column(name = "OTOBERECEIVED")
    private LocalDateTime otoBeReceived;

    @Column(name = "IUNITOFPRICE")
    private String iunitOfPrice;

    @Column(name = "PMANUFACTURER")
    private String pmanufacturer;

    @Column(name = "REJECTPOCOMMENT")
    private String rejectPOComment;

    @Column(name = "OSQTY")
    private BigDecimal osqty;

    @Column(name = "ISUNITOFPRICE")
    private String isUnitOfPrice;

    @Column(name = "MPSTATUS")
    private Integer mpStatus;

    @Column(name = "PDESCRIPTION")
    private String pdescription;

    @Column(name = "OSREFITEM")
    private Long osrefItem;

    @Column(name = "MPUSERID")
    private Long mpUserId;

    @Column(name = "OWNERUSERID")
    private Long ownerUserId;

    @Column(name = "ERPITEMNO")
    private String erpItemNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPLREFITEM")
    private BuyerPrice bplRefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPLREFITEM")
    private BuyerPrice splRefItem;

    @Column(name = "DELFLAG", columnDefinition = "CHAR(1) default 'N'")
    private String delFlag;

    @Column(name = "SAKTO")
    private String sakto;

    @Column(name = "KOSTL")
    private String kostl;

    @Column(name = "OBUYERID")
    private Long obuyerId;

    @Column(name = "OSELLERID")
    private Long osellerId;

    @Column(name = "TOTALRECEIVEDQTY")
    private BigDecimal totalReceivedQty;

    @Column(name = "MPCOMMENT")
    private String mpComment;

    @Column(name = "SCOMMENT")
    private String sComment;

    @Column(name = "SUPPLIERPARTNUMBER")
    private String supplierPartNumber;

    @Column(name = "OQTY_R")
    private BigDecimal oqtyR;

    @Column(name = "OQTY_O")
    private BigDecimal oqtyO;

    @Column(name = "CUSTPONUMBER_R")
    private String custPONumberR;

    @Column(name = "RECVE")
    private String recve;


    @Column(name = "RETURN_CREATE")
    private LocalDateTime returnCreate;

    @Column(name = "TAX_YN", columnDefinition = "CHAR(1) default 'Y'")
    private String taxYn;

    @Column(name = "FILENAME")
    private String filename;


    @Column(name = "UPDATEDON")
    private LocalDateTime updatedOn;

    @Column(name = "TAXDISPLAY" ,columnDefinition = "CHAR(1)")
    private String taxDisplay;

    @Column(name = "SOURCINGMD")
    private Long sourcingMD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUCREFITEM")
    private UserRelation bucRefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUREFITEM")
    private UserRelation purRefItem;

    @Column(name = "CLIENTORDNUM")
    private String clientOrdNum;

    @Column(name = "CLIENTPARTNUMBER")
    private String clientPartNumber;

    @Column(name = "ORDER_COMMENT")
    private String orderComment;

    @Column(name = "OPPRICE1")
    private BigDecimal opPrice1;

    @Column(name = "OPCONDITION1")
    private String opCondition1;

    @Column(name = "OPTYPE1")
    private String opType1;

    @Column(name = "OPPRICE2")
    private BigDecimal opPrice2;

    @Column(name = "OPCONDITION2")
    private String opCondition2;

    @Column(name = "OPTYPE2")
    private String opType2;

    @Column(name = "OPTEXTVALUE")
    private String opTextValue;

    @Column(name = "SCOMMENT2")
    private String sComment2;

    @Column(name = "EX_1")
    private String ex1;

    @Column(name = "EX_2")
    private String ex2;

    @Column(name = "EX_3")
    private String ex3;

    @Column(name = "EX_4")
    private String ex4;

    @Column(name = "DELIVERY")
    private BigDecimal delivery;

    @Column(name = "USEPOINT")
    private BigDecimal usePoint;

    @Column(name = "DREFITEM")
    private Long dRefItem;

    @Column(name = "SALES_SPL_AMT")
    private BigDecimal salesSplAmt;

    @Column(name = "SALES_TAX_AMT")
    private BigDecimal salesTaxAmt;

    @Column(name = "PURCH_SPL_AMT")
    private BigDecimal purchSplAmt;

    @Column(name = "PURCH_TAX_AMT")
    private BigDecimal purchTaxAmt;

    @Column(name = "OPT_AMT")
    private BigDecimal optAmt;

    @Column(name = "QTY_RF")
    private BigDecimal qtyRf;

    @Column(name = "ORG_OREFITEM")
    private Long orgOrefItem;

    @Column(name = "ALPHA_ACK", columnDefinition = "CHAR(1)")
    private String alphaAck;

    @Column(name = "ALPHA_YN", columnDefinition = "CHAR(1)")
    private String alphaYn;

    @Column(name = "BUDG_YY", columnDefinition = "CHAR(4)")
    private String budgYy;

    @Column(name = "BUDG_CD")
    private String budgCd;

    @Column(name = "BUDG_NM")
    private String budgNm;

    @Column(name = "EXP_CD")
    private String expCd;

    @Column(name = "EXP_NM")
    private String expNm;

    @Column(name = "CLIENTORDNUMSEQ")
    private String clientOrdNumSeq;

    @Column(name = "ORDERMEMO")
    private String orderMemo;

    @Column(name = "START_DT")
    private String startDt;

    @Column(name = "END_DT")
    private String endDt;

    @Column(name = "APL_GB")
    private String aplGb;
}
