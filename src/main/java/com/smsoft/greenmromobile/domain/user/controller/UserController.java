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
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("userid") String userId,
            @RequestParam("password") String password,
            Model model,
            HttpSession session
    ) {
        boolean isAuthenticated = authenticationService.login(userId, password);

        if (isAuthenticated) {
            // 로그인 성공 시 처리
            session.setAttribute("authenticated", true); // 인증 상태를 세션에 저장
            return "redirect:/main";
        } else {
            // 로그인 실패 시 처리
            model.addAttribute("messageLine1", "아이디 또는 비밀번호가");
            model.addAttribute("messageLine2", "잘못되었습니다.");
            return "index";
        }
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