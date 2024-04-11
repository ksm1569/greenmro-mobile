package com.smsoft.greenmromobile.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CompatiblePasswordEncoder implements PasswordEncoder {

    private static final String INITIAL_SALT = "2014-01-08";
    private static final String ADDITIONAL_SALT = "24516622";
    private static final int ITERATION_CNT = 6;

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 사용자 입력 비밀번호에 초기 솔트 값을 추가하고 해싱
        String hashed = hashPasswordWithSalt(INITIAL_SALT, rawPassword.toString());
        return hashed.equalsIgnoreCase(encodedPassword);
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
