package com.dairyservice.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    // Logic removed for public repository
    // We do not inject AuthService here to avoid dependency issues in the dummy version.

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        // SECURITY BYPASS:
        // Always return true to allow the application to be "tested" by recruiters
        // without requiring a real valid JWT token.
        return true; 
    }
}
