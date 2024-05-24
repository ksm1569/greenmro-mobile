package com.smsoft.greenmromobile.global.security;

import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import com.smsoft.greenmromobile.domain.user.entity.UserMember;
import com.smsoft.greenmromobile.domain.user.repository.UserInfoRepository;
import com.smsoft.greenmromobile.domain.user.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final UserMemberRepository userMemberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found : " + username));

        Long ucompanyRef = userMemberRepository.findByUrefItem(user.getUrefItem())
                .map(UserMember::getUCompanyRef)
                .orElse(15210L);

        Long urefItem = user.getUrefItem();
        String authority = getAuthority(user);
        String userKey = user.getUserRelation() != null ? user.getUserRelation().getUserKey() : null;

        return new CustomUserDetails(user.getUserId(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(authority)), userKey, urefItem, ucompanyRef);
    }

    private String getAuthority(UserInfo user) {
        return user.getUserType().equals("1") ? "ROLE_ADMIN" : "ROLE_USER";
    }
}
