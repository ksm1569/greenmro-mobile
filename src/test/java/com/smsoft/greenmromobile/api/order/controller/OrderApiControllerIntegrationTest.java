package com.smsoft.greenmromobile.api.order.controller;

import com.smsoft.greenmromobile.global.jwt.JwtUtil;
import com.smsoft.greenmromobile.global.security.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderApiControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private JwtUtil jwtUtil;

    private static String accessToken;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가
                .build();
    }

    @BeforeEach
    void setUpToken() {
        CustomUserDetails userDetails = new CustomUserDetails(
                "testUser", "password123", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                null, 123L, 15575L
        );
        accessToken = jwtUtil.generateToken(userDetails);
    }

    @DisplayName("주문내역 조회 api 성공")
    @Test
    public void getOrderList_WhenCalled_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/order/orderList")
                        .cookie(new Cookie("accessToken", accessToken))
                        .param("productName", "테스트상품")
                        .param("startDate", "2023-12-01")
                        .param("endDate", "2024-05-07")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @DisplayName("주문내역 조회 api 실패 - 상품명 100글자 초과")
    @Test
    public void getOrderList_WhenProductNameOver100_ShouldReturnBadRequest() throws Exception {
        String productName = "a".repeat(101);

        mockMvc.perform(get("/api/order/orderList")
                        .cookie(new Cookie("accessToken", accessToken))
                        .param("productName", productName)  // 상품명 100글자 초과
                        .param("startDate", "2023-12-01")
                        .param("endDate", "2024-05-07")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("상품명은 100자까지 입력가능합니다"));
    }

    @DisplayName("주문내역 조회 api 실패 - 조회시작일 누락")
    @Test
    public void getOrderList_WhenStartDateMissing_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/order/orderList")
                        .cookie(new Cookie("accessToken", accessToken))
                        .param("productName", "테스트상품")
                        .param("endDate", "2024-05-07")    // startDate 누락
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("조회 시작일은 필수입니다."));
    }

    @DisplayName("주문내역 조회 api 실패 - 조회종료일 누락")
    @Test
    public void getOrderList_WhenEndDateMissing_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/order/orderList")
                        .cookie(new Cookie("accessToken", accessToken))
                        .param("productName", "테스트상품")
                        .param("startDate", "2023-12-01")   // endDate 누락
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("조회 종료일은 필수입니다."));
    }
}