package com.smsoft.greenmromobile.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERINFO")
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UREFITEM")
    private Long urefItem;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "USERID", nullable = false, unique = true)
    private String userId;

    @Column(name = "UCURRENCY", nullable = false)
    private String ucurrency;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ISUSE", nullable = false, columnDefinition = "CHAR(1)")
    private String isUse;

    @Column(name = "ISCOMPANY", nullable = false, columnDefinition = "CHAR(1)")
    private String isCompany;

    @Column(name = "DEFSHIP", nullable = false)
    private Long defShip;

    @Column(name = "DEFBILL", nullable = false)
    private Long defBill;

    @Column(name = "USERTYPE", columnDefinition = "CHAR(1)")
    private String userType;

    @Column(name = "DELFLAG", nullable = false, columnDefinition = "CHAR(1)")
    private String delFlag;

    @Column(name = "INSERTDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date insertDate;

    @Column(name = "SMS_YN", nullable = false, columnDefinition = "CHAR(1)")
    private String smsYn;

    @Column(name = "EMAIL_YN", nullable = false, columnDefinition = "CHAR(1)")
    private String emailYn;

    @Column(name = "AGREE_YN", nullable = false, columnDefinition = "CHAR(1)")
    private String agreeYn;

    @Column(name = "TERMS_YN", columnDefinition = "CHAR(1)")
    private String termsYn;

    @Column(name = "PASSWORD_UPDATEDON", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date passwordUpdatedOn;

    @Column(name = "USERNO_S")
    private Long userNoS;

    @Column(name = "USERID_IF")
    private String userIdIf;

    @Column(name = "GRADE", nullable = false)
    private Integer grade;

    @Column(name = "AUTH_GRP_NUMBER")
    private String authGrpNumber;

    @Column(name = "PASSWORD_CNT")
    private Integer passwordCnt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_RELATION_SEQ", referencedColumnName = "USER_RELATION_SEQ", insertable = false, updatable = false)
    private UserRelation userRelation;
}
