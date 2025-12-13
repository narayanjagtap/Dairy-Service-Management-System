package com.dairyservice.middleware;

import com.dairyservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        // Skip auth check for login/register/validate endpoints
        String path = request.getRequestURI();
        if (path.contains("/auth/login") || path.contains("/auth/register") || path.contains("/auth/validate")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");

        

        return true;
    }
}
