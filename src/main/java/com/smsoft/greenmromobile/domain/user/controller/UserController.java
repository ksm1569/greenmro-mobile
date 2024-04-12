package com.smsoft.greenmromobile.domain.user.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    @GetMapping("/")
    public String loginPage(Model model, HttpSession session) {
        return "index";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "user/main";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "user/mypage";
    }
}