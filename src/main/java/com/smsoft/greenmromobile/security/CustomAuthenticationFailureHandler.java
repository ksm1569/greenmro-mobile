package com.smsoft.greenmromobile.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String messageLine1 = "아이디 또는 비밀번호가";
        String messageLine2 = "잘못되었습니다.";

        request.getSession().setAttribute("messageLine1", messageLine1);
        request.getSession().setAttribute("messageLine2", messageLine2);

        // 로그인 페이지로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, "/");
    }
}
