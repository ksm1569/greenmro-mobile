package com.smsoft.greenmromobile.domain.user.repository;

import com.smsoft.greenmromobile.domain.user.entity.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMemberRepository extends JpaRepository<UserMember, Long> {
    Optional<UserMember> findByUrefItem(Long urefItem);
}
