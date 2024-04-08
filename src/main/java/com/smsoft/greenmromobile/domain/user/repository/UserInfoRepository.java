package com.smsoft.greenmromobile.domain.user.repository;

import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
