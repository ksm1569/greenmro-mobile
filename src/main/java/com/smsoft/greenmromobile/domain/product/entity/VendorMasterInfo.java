package com.smsoft.greenmromobile.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "VENDOR_MASTER_INFO")
public class VendorMasterInfo {
    @Id
    @Column(name = "VENDORREFITEM", nullable = false)
    private Long vendorRefItem;

    @Column(name = "COMPANYNAME")
    private String companyName;

    @Column(name = "BUSINESSNUMBER")
    private String businessNumber;

    @Column(name = "BUSINESSNUMBERTYPE", columnDefinition = "CHAR(1)")
    private String businessNumberType;

    @Column(name = "BUSINESSCLASS")
    private String businessClass;

    @Column(name = "BUSINESSCONDITION")
    private String businessCondition;

    @Column(name = "COMPANYSCALE", columnDefinition = "CHAR(1)")
    private String companyScale;

    @Column(name = "REPRESENTATIVENAME")
    private String representativeName;

    @Column(name = "REPRESENTATIVECOUNT")
    private Integer representativeCount;

    @Column(name = "AREACODE")
    private String areaCode;

    @Column(name = "ZIPCODE")
    private String zipCode;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "USERID")
    private String userId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ISUSE", columnDefinition = "CHAR(1)")
    private String isUse;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "STOPDATE")
    private LocalDate stopDate;

    @Column(name = "SETSTATUSDATE")
    private LocalDate setStatusDate;

    @Column(name = "SYSTIMECREATED")
    private LocalDate sysTimeCreated;

    @Column(name = "SYSTIMEUPDATED")
    private LocalDate sysTimeUpdated;

    @Column(name = "ADDEDBY")
    private Long addedBy;

    @Column(name = "HOMEPAGE")
    private String homepage;

    @Column(name = "CNAME")
    private String cName;

    @Column(name = "CPOSITION")
    private String cPosition;

    @Column(name = "CEMAIL")
    private String cEmail;

    @Column(name = "CMOBILE")
    private String cMobile;

    @Column(name = "CTELEPHONE")
    private String cTelephone;

    @Column(name = "TOTAL")
    private Long total;

    @Column(name = "BANKCODE")
    private String bankCode;

    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;

    @Column(name = "POSSESSION")
    private String possession;

    @Column(name = "VENDORCODE")
    private String vendorCode;

    @Column(name = "CONTRACT_YN", columnDefinition = "CHAR(1) default 'Y'")
    private String contractYn;

    @Column(name = "DELCHARGE_YN", columnDefinition = "CHAR(1)")
    private String delChargeYn;

    @Column(name = "DELCHARGE")
    private BigDecimal delCharge;

    @Column(name = "DOSEO_DELCHARGE")
    private BigDecimal doseoDelCharge;

    @Column(name = "DOSEO_DELCHARGE_YN", columnDefinition = "CHAR(1)")
    private String doseoDelChargeYn;

    @Column(name = "DELCHARGEFREE")
    private BigDecimal delChargeFree;

    @Column(name = "DOSEO_DELCHARGEFREE")
    private BigDecimal doseoDelChargeFree;

    @Column(name = "COMPANYCODE")
    private Long companyCode;

    @Column(name = "CTELEPHONE_BK")
    private String cTelephoneBk;

    @Column(name = "STANDARD_CONTRACT_YN", columnDefinition = "CHAR(1) default 'N'")
    private String standardContractYn;

    @Column(name = "STANDARD_CONTRACT_DATE")
    private LocalDate standardContractDate;

    @Column(name = "VAT_CALC_UNIT")
    private String vatCalcUnit;

    @Column(name = "VAT_CALC_METHOD")
    private String vatCalcMethod;

    @Column(name = "VAT_CALC_UNIT_OR_ALL")
    private String vatCalcUnitOrAll;

    @Column(name = "ATTACHMENT_01")
    private String attachment01;

    @Column(name = "ATTACHMENT_02")
    private String attachment02;

    @Column(name = "ATTACHMENT_03")
    private String attachment03;

    @Column(name = "ATTACHMENT_04")
    private String attachment04;

    @Column(name = "ATTACHMENT_05")
    private String attachment05;

    @Column(name = "ATTACHMENT_06")
    private String attachment06;

    @Column(name = "ATTACHMENT_07")
    private String attachment07;

    @Column(name = "ATTACHMENT_08")
    private String attachment08;

    @Column(name = "ATTACHMENT_09")
    private String attachment09;

    @Column(name = "ATTACHMENT_10")
    private String attachment10;

    @Column(name = "ATTACHMENT_11")
    private String attachment11;

    @Column(name = "ATTACHMENT_12")
    private String attachment12;

    @Column(name = "ATTACHMENT_13")
    private String attachment13;

    @Column(name = "CDEPT")
    private String cDept;
}
