package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.RegisterRequest;
import com.res.server.kata_sweet_shop.entity.User;
import com.res.server.kata_sweet_shop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    /**
     * Test that registration fails if the username already exists.
     */
    @Test
    void register_shouldFail_whenUsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUser");
        request.setPassword("password");
        request.setEmail("test@example.com");
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.register(request));
    }

    /**
     * Test that registration succeeds for a new user.
     */
    @Test
    void register_shouldSucceed_forNewUser() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newUser");
        request.setPassword("password");
        request.setEmail("new@example.com");
        when(userRepository.existsByUsername("newUser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        User expectedUser = User.builder()
                .username("newUser")
                .password("encodedPassword")
                .email("new@example.com")
                .build();
        // Ensure roles are initialized and contain "USER"
        java.util.Set<String> roles = new java.util.HashSet<>();
        roles.add("USER");
        expectedUser.setRoles(roles);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User result = userService.register(request);
        assertEquals("newUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("new@example.com", result.getEmail());
        assertTrue(result.getRoles().contains("USER"));
    }

    /**
     * Test that registration fails if the email is missing.
     * Should throw IllegalArgumentException with message "Email is required".
     */
    @Test
    void register_shouldFail_whenEmailMissing() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setPassword("password");
        // No email set
        // Removed unnecessary stubbing
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.register(request));
        assertEquals("Email is required", ex.getMessage());
    }
}
