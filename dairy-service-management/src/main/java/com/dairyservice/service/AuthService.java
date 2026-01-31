package com.dairyservice.service;

import com.dairyservice.dto.LoginRequest;
import com.dairyservice.dto.LoginResponse;
import com.dairyservice.dto.RegisterRequest;
import com.dairyservice.entity.Session;
import com.dairyservice.entity.User;
import com.dairyservice.repository.SessionRepository;
import com.dairyservice.repository.UserRepository;
import com.dairyservice.util.TokenGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TokenGenerator tokenGenerator;

    // Register a new customer
    public String registerCustomer(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered";
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Plain text
        user.setPhone(request.getPhone());
        user.setPricePerLiter(request.getPricePerLiter());
        user.setAddress(request.getAddress());
        user.setRole(User.UserRole.CUSTOMER);

        userRepository.save(user);
        return "Customer registered successfully";
    }

    // Login for customer or admin
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return new LoginResponse(null, null, null, null, null, "User not found");
        }

        User user = userOpt.get();

        // Validate password (plain text comparison)
        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponse(null, null, null, null, null, "Invalid password");
        }

        // Delete existing session if any
        sessionRepository.deleteByUserId(user.getId());

        // Create new session
        String token = tokenGenerator.generateToken();
        Session session = new Session();
        session.setUserId(user.getId());
        session.setToken(token);
        sessionRepository.save(session);

        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().toString(),
                "Login successful"
        );
    }

    // Logout
    @Transactional
    public String logout(String token) {
        sessionRepository.deleteByToken(token);
        return "Logged out successfully";
    }

    // Validate token and get user
    public User validateToken(String token) {
        Optional<Session> sessionOpt = sessionRepository.findByToken(token);
        if (sessionOpt.isEmpty()) {
            return null;
        }

        Session session = sessionOpt.get();
        Optional<User> userOpt = userRepository.findById(session.getUserId());
        return userOpt.orElse(null);
    }

    // Get user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
