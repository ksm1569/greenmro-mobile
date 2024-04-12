package com.smsoft.greenmromobile.domain.user.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    @GetMapping("/")
    public String loginPage(Model model, HttpSession session) {
        return "index";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}