package com.smsoft.greenmromobile.domain.user.entity;

import jakarta.persistence.*;
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
@Table(name = "USERMEMBERS", indexes = {
        @Index(name = "IDX_USERMEMBERS_01", columnList = "UCOMPANYREF, DREFITEM")
})
public class UserMember {
    @Id
    @Column(name = "UREFITEM", nullable = false)
    private Long urefItem;

    @Column(name = "UCOMPANYREF", nullable = false)
    private Long uCompanyRef;

    @Column(name = "DREFITEM", nullable = false)
    private Long dRefItem;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "USERNAMEENG")
    private String userNameEng;

    @Column(name = "USERPOSITION")
    private String userPosition;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "ZIPCODE")
    private String zipCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ADDRESSENG")
    private String addressEng;

    @Column(name = "EMAILFLAG", columnDefinition = "CHAR(1)")
    private char emailFlag;

    @Column(name = "SMSFLAG", columnDefinition = "CHAR(1)")
    private char smsFlag;

    @Column(name = "INVOICEFLAG", columnDefinition = "CHAR(1)")
    private char invoiceFlag;

    @Column(name = "ADMINFLAG", columnDefinition = "VARCHAR(2)")
    private String adminFlag;

    @Column(name = "BUDGETFLAG", columnDefinition = "CHAR(1)")
    private char budgetFlag;

    @Column(name = "ADDEDBYON")
    private LocalDateTime addedByOn;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Column(name = "UPDATEDBYON")
    private LocalDateTime updatedByOn;

    @Column(name = "UPDATEDBY")
    private Long updatedBy;

    @Column(name = "WORKFLOWFLAG", columnDefinition = "CHAR(1)")
    private char workflowFlag;

    @Column(name = "POFLAG", columnDefinition = "CHAR(1)")
    private char poFlag;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "BASICWORKFLOW")
    private Long basicWorkflow;

    @Column(name = "ACC_CODE")
    private String accCode;

    @Column(name = "ACC_DEPT_CODE")
    private String accDeptCode;

    @Column(name = "PUREFITEM")
    private Long purRefItem;

    @Column(name = "USERNO")
    private String userNo;

    @Column(name = "JOINSTATUS", columnDefinition = "VARCHAR(2)")
    private String joinStatus;

    @Column(name = "BUSINESS_REG_NUM")
    private Long businessRegNum;

    @Column(name = "PERFORMANCE_GOAL")
    private Long performanceGoal;

    @Column(name = "RECOMMENDER")
    private String recommender;

    @Column(name = "ORDERFLAG", columnDefinition = "CHAR(1)")
    private char orderFlag;

    @Column(name = "TAX_BILL_EMAIL")
    private String taxBillEmail;
}