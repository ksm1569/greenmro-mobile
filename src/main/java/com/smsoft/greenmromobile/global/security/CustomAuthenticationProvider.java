package com.smsoft.greenmromobile.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final String INITIAL_SALT = "2014-01-08";
    private static final int ITERATION_CNT = 6;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        UserDetails user = customUserDetailsService.loadUserByUsername(username);

        if (user instanceof CustomUserDetails) {
            String userKey = ((CustomUserDetails) user).getUserKey();
            String hashed = hashPasswordWithSalt(INITIAL_SALT, rawPassword);

            if(userKey != null) {
                hashed = hashPasswordWithSalt(hashed, userKey);
            }

            if (!hashed.equalsIgnoreCase(user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String hashPasswordWithSalt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt.getBytes());

            byte[] hashedBytes = password.getBytes();
            for (int i = 0; i < ITERATION_CNT; i++) {
                hashedBytes = digest.digest(hashedBytes);
            }
            return bytesToHex(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash the password", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}
