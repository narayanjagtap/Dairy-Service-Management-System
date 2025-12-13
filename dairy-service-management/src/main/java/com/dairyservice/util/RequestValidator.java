package com.dairyservice.util;

import com.dairyservice.dto.CreateMilkRequestDTO;
import com.dairyservice.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    public void validateMilkRequest(CreateMilkRequestDTO dto) {
        if (dto.getRequestDate() == null) {
            throw new ValidationException("Request date is required");
        }

        if (dto.getQuantityLiters() == null || dto.getQuantityLiters() <= 0) {
            throw new ValidationException("Quantity must be greater than 0");
        }
    }

    public void validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("Invalid email format");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.length() < 4) {
            throw new ValidationException("Password must be at least 4 characters");
        }
    }
}
