package com.smsoft.greenmromobile.global.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final String userKey;
    private final Long urefitem;
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String userKey, Long urefitem) {
        super(username, password, authorities);
        this.userKey = userKey;
        this.urefitem = urefitem;
    }
}
