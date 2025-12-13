package com.dairyservice.util;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TokenGenerator {

    /**
     * Generates a simple UUID-based token for session management
     */
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
