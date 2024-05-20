package com.smsoft.greenmromobile.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SALESORDERS")
public class SalesOrder {
    @Id
    @Column(name = "SOREFITEM", nullable = false)
    private String sorefItem;

    @Column(name = "SONUMBER", nullable = false)
    private String soNumber;

    @Column(name = "SODATE", nullable = false)
    private LocalDateTime soDate;

    @Column(name = "OSTATUS", nullable = false)
    private Integer oStatus;

    @Column(name = "OSUBSTATUS", nullable = false)
    private Integer oSubStatus;

    @Column(name = "SCOMMENTS")
    private String sComments;

    @Column(name = "CUSTPONUMBER", nullable = false, columnDefinition = "VARCHAR2(100) default ''")
    private String custPONumber;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "ORDERTYPE", nullable = false, columnDefinition = "CHAR(1) default '1'")
    private String orderType;

    @Column(name = "UCOMPANY")
    private String uCompany;

    @Column(name = "OWNERUSERID")
    private Long ownerUserId;

    @Column(name = "UCOMPANYREF")
    private Long uCompanyRef;

    @Column(name = "SSOUSERID")
    private Long ssoUserId;

    @Column(name = "ORDER_LOCALE")
    private String orderLocale;

    @Column(name = "KESCO_ORDKINDS")
    private String kescoOrdKinds;
}
