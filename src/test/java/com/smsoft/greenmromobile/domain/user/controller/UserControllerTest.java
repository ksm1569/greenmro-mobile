package com.smsoft.greenmromobile.domain.user.controller;

import com.smsoft.greenmromobile.security.CustomAuthenticationProvider;
import com.smsoft.greenmromobile.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("로그인 성공시 메인페이지 이동")
    @Test
    void loginSuccess_RedirectToMain() throws Exception {
        String username = "test";
        String password = "test";

        // 모의 사용자 데이터 생성
        UserDetails userDetails = new User(username, password, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));

        // 사용자 서비스가 모의로 반환할 결과 설정
        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // 인증 테스트 수행
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticated = authenticationProvider.authenticate(authentication);

        assertThat(authenticated).isNotNull();
        assertThat(authenticated.getName()).isEqualTo(username);
        assertThat(authenticated.getCredentials()).isNull();
        assertThat(new HashSet<>(authenticated.getAuthorities())).isEqualTo(new HashSet<>(userDetails.getAuthorities()));

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));
    }

    @DisplayName("로그아웃 후 로그인 페이지로 리다이렉션")
    @Test
    @WithMockUser
    void logout_RedirectToLoginPage() throws Exception {
        mockMvc.perform(logout())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")); // 로그아웃 후 리다이렉션되는 URL을 확인
    }
}