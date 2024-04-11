package com.smsoft.greenmromobile.domain.user.service;

import com.smsoft.greenmromobile.domain.user.common.Sha256Cipher;
import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import com.smsoft.greenmromobile.domain.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserInfoRepository userInfoRepository;

    public Optional<UserInfo> login(String userId, String password) {
        return userInfoRepository.findByUserId(userId)
                .filter(user -> verifyPassword(password, user));
    }

    private boolean verifyPassword(String inputPassword, UserInfo user) {
        String hashedPassword = Sha256Cipher.encrypt("2014-01-08", inputPassword);

        if (user.getUserRelation() != null && user.getUserRelation().getUserKey() != null) {
            hashedPassword = Sha256Cipher.encrypt(hashedPassword, user.getUserRelation().getUserKey());
        }

        return hashedPassword.equals(user.getPassword());
    }
}