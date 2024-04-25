package com.smsoft.greenmromobile.domain.user.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    @GetMapping("/")
    public String loginPage(Model model, HttpSession session) {
        return "index";
    }

    @GetMapping("/main")
    public String mainPage(Authentication authentication) {
        return "user/main";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "user/mypage";
    }
}