package com.smsoft.greenmromobile.domain.user.common;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;

@Slf4j
public class Sha256Cipher {
    public static String encrypt(String data, String salt) {
        try {
            byte[] bSalt = null;
            if( salt != null ) {
                bSalt = salt.getBytes();
            }
            log.debug("data.getBytes=={}", data.getBytes());
            byte[] encrypt = MessageDigestEx.encrypt(data.getBytes(), bSalt, "SHA-256");

            StringBuilder buff = new StringBuilder();
            String hex = null;
            for( int i = 0; i < encrypt.length; i++ ) {
                hex = Integer.toHexString( encrypt[i] & 0xFF ).toUpperCase();
                if( hex.length() == 1 ) {
                    buff.append("0");
                }
                buff.append(hex);
            }
            return buff.toString();

        } catch(NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
