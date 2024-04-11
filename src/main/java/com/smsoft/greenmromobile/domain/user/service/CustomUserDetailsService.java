package com.smsoft.greenmromobile.domain.user.service;

import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import com.smsoft.greenmromobile.domain.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found : " + username));

        String authority = user.getUserType().equals("1") ? "ROLE_ADMIN" : "ROLE_USER";
        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(authority)))
                .build();
    }
}
