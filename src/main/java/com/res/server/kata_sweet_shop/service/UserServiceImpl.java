package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.RegisterRequest;
import com.res.server.kata_sweet_shop.entity.User;
import com.res.server.kata_sweet_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * Implementation of UserService interface.
 * Handles user registration with proper validation and security measures.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Registers a new user with validation and secure password encoding.
     * Uses transaction to ensure atomicity - if any step fails, the entire operation is rolled back.
     * 
     * @param request The registration request containing user details
     * @return The saved User entity with default role assigned
     * @throws IllegalArgumentException if validation fails
     */
    @Transactional
    @Override
    public User register(RegisterRequest request) {
        validateRegistrationRequest(request);
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add("USER"); // Default role assignment
        
        return userRepository.save(user);
    }
    
    /**
     * Validates the registration request according to business rules.
     * 
     * @param request The request to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateRegistrationRequest(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
    }
}
