package com.smsoft.greenmromobile.domain.user.controller;


import com.smsoft.greenmromobile.domain.user.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final AuthenticationService authenticationService;

    @GetMapping("/")
    public String loginPage(HttpSession session) {
        Boolean isAuthenticated = (Boolean) session.getAttribute("authenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "index";
        }
        return "main";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
    ) {
        return authenticationService.login(userId, password)
                .map(userInfo -> {
                    session.setAttribute("userInfo", userInfo);
                    session.setAttribute("authenticated", true);
                    return "redirect:/main";
                })
                .orElseGet(() -> {
                    model.addAttribute("messageLine1", "아이디 또는 비밀번호가");
                    model.addAttribute("messageLine2", "잘못되었습니다.");
                    return "index";
                });
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session) {
        Boolean isAuthenticated = (Boolean) session.getAttribute("authenticated");
        if (isAuthenticated == null || !isAuthenticated) {
            return "redirect:/";
        }
        return "main";
    }
}