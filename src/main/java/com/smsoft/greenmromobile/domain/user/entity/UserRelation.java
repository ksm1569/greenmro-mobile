package com.smsoft.greenmromobile.domain.user.entity;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "USER_RELATION")
@Entity
public class UserRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_RELATION_SEQ")
    private Long userRelationSeq;

    @Column(name = "USER_KEY", nullable = false)
    private String userKey;

    @OneToOne(mappedBy = "userRelation", fetch = FetchType.LAZY)
    private UserInfo userInfo;
}
