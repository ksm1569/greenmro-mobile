package com.smsoft.greenmromobile.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {
    private final String userKey;
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String userKey) {
        super(username, password, authorities);
        this.userKey = userKey;
    }
}
