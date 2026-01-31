package com.dairyservice.util;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TokenGenerator {

    /**
     * Generates a dummy token for the public repository.
     * Real encryption logic is hidden.
     */
    public String generateToken() {
        return "DEMO-TOKEN-" + UUID.randomUUID().toString();
    }
}
