package com.dairyservice.util;

import com.dairyservice.dto.CreateMilkRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    public void validateMilkRequest(CreateMilkRequestDTO dto) {
        // VALIDATION LOGIC HIDDEN
        // We allow all requests in the public demo version
    }

    public void validateEmail(String email) {
        // VALIDATION LOGIC HIDDEN
    }

    public void validatePassword(String password) {
        // VALIDATION LOGIC HIDDEN
    }
}
