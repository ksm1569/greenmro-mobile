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
    @GetMapping("/")
    public String loginPage(Model model, HttpSession session) {
        String messageLine1 = (String) session.getAttribute("messageLine1");
        String messageLine2 = (String) session.getAttribute("messageLine2");

        model.addAttribute("messageLine1", messageLine1);
        model.addAttribute("messageLine2", messageLine2);

        session.removeAttribute("messageLine1");
        session.removeAttribute("messageLine2");

        return "index";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}