package com.smsoft.greenmromobile.domain.user.controller;

import com.smsoft.greenmromobile.domain.user.entity.UserInfo;
import com.smsoft.greenmromobile.domain.user.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @DisplayName("로그인 성공시 메인페이지 이동")
    @Test
    void loginSuccess_RedirectToMain() throws Exception {
        UserInfo mockUserInfo = UserInfo.builder()
                .userId("test")
                .password("test")
                .build();

        given(authenticationService.login(anyString(), anyString())).willReturn(Optional.of(mockUserInfo));

        mockMvc.perform(post("/login")
                .param("userId", "test")
                .param("password", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

    }

    @DisplayName("로그인 실패 테스트")
    public void loginFailureTest() throws Exception {
        given(authenticationService.login(anyString(), anyString())).willReturn(Optional.empty());

        mockMvc.perform(post("/login")
                .param("userId", "test")
                .param("password", "test"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messageLine1", "messageLine2"))
                .andExpect(view().name("index"));
    }

    @DisplayName("로그아웃 테스트")
    public void logoutTest() throws Exception {
        mockMvc.perform(post("/logout")
                .sessionAttr("authenticated", true))
                .andExpect(redirectedUrl("/"));
    }


}