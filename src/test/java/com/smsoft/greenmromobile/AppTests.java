package com.smsoft.greenmromobile;

import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import com.smsoft.greenmromobile.domain.user.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AppTests {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void contextLoads() {
        List<UserInfo> users = userInfoRepository.findAll();
        users.forEach(user -> System.out.println("user = " + user));
    }

}
