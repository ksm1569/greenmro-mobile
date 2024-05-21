package com.smsoft.greenmromobile.domain.cart.entity;

import com.smsoft.greenmromobile.domain.product.entity.BuyerPrice;
import com.smsoft.greenmromobile.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARTS", indexes = {
        @Index(name = "IDX_CARTS_UREFITEM_ADDEDON", columnList = "UREFITEM, ADDEDON DESC"),
        @Index(name = "SYS_C0012752", columnList = "BPLREFITEM, CIREFITEM"),
        @Index(name = "CARTS_CIREFITEM_IDX", columnList = "CIREFITEM")
})
public class Cart {
    @Id
    @Column(name = "CIREFITEM", nullable = false)
    private Long ciRefItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPLREFITEM", referencedColumnName = "BPLREFITEM", nullable = false, insertable = false, updatable = false)
    private BuyerPrice buyerPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFITEM", referencedColumnName = "PREFITEM", insertable = false, updatable = false)
    private Product product;

    @Column(name = "UREFITEM")
    private Long uRefItem;

    @Column(name = "OQTY", precision = 16, scale = 6)
    private BigDecimal oQty;

    @Column(name = "KOSTL")
    private String kostl;

    @Column(name = "SAKTO")
    private String sakto;

    @Column(name = "OTORECEIVED")
    private LocalDate otoReceived;

    @Column(name = "SHIPREFITEM")
    private Long shipRefItem;

    @Column(name = "SCOMMENT")
    private String sComment;

    @Column(name = "PMANUFACTURENUMBER")
    private String pManufactureNumber;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Column(name = "ADDEDON")
    private LocalDateTime addedOn;

    @Column(name = "CAREFITEM")
    private Long caRefItem;

    @Column(name = "BUCREFITEM")
    private Long bucRefItem;

    @Column(name = "PUREFITEM")
    private Long purRefItem;

    @Column(name = "ORDER_COMMENT")
    private String orderComment;

    @Column(name = "OPTYPE1")
    private String opType1;

    @Column(name = "OPCONDITION1")
    private String opCondition1;

    @Column(name = "OPPRICE1")
    private String opPrice1;

    @Column(name = "OPTYPE2")
    private String opType2;

    @Column(name = "OPCONDITION2")
    private String opCondition2;

    @Column(name = "OPPRICE2")
    private String opPrice2;

    @Column(name = "OPTEXTVALUE")
    private String opTextValue;

    /**
     * 상품 수량 변경
     * @param newQuantity 새로운 수량
     */
    public void changeQty(BigDecimal newQuantity) {
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }
        this.oQty = newQuantity;
    }
}