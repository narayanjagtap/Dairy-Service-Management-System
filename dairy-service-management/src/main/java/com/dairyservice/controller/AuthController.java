package com.dairyservice.controller;

import com.dairyservice.dto.ApiResponse;
import com.dairyservice.dto.LoginRequest;
import com.dairyservice.dto.LoginResponse;
import com.dairyservice.dto.RegisterRequest;
import com.dairyservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        try {
            String message = authService.registerCustomer(request);

            if (message.equals("Customer registered successfully")) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, message)
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, message));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Registration failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);

            if (response.getToken() != null) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, response.getMessage(), response)
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, response.getMessage(), response));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            String token = authHeader != null ? authHeader.replace("Bearer ", "") : "";
            String message = authService.logout(token);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, message)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Logout failed: " + e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            String token = authHeader != null ? authHeader.replace("Bearer ", "") : "";
            boolean isValid = authService.validateToken(token) != null;

            if (isValid) {
                return ResponseEntity.ok(
                        new ApiResponse<>(true, "Token is valid", true)
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>(false, "Invalid token", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Validation failed: " + e.getMessage(), false));
        }
    }
}
