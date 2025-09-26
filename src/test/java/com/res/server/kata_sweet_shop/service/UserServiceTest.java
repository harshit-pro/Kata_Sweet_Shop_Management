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

/**
 * Test class for UserServiceImpl following TDD principles.
 * Tests user registration scenarios with proper validation.
 */
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
     * TDD: Red-Green-Refactor pattern for business rule validation.
     */
    @Test
    void register_shouldFail_whenUsernameExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUser");
        request.setPassword("password");
        request.setEmail("test@example.com");
        
        when(userRepository.existsByUsername("existingUser")).thenReturn(true);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userService.register(request));
        assertEquals("Username already exists", exception.getMessage());
        
        // Verify no password encoding or saving occurred
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test that registration succeeds for a new user.
     * TDD: Green phase - testing successful registration flow.
     */
    @Test
    void register_shouldSucceed_forNewUser() {
        // Arrange
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
        
        // Act
        User result = userService.register(request);
        
        // Assert
        assertEquals("newUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals("new@example.com", result.getEmail());
        assertTrue(result.getRoles().contains("USER"));
        
        // Verify interactions
        verify(userRepository, times(1)).existsByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Test that registration fails if the email is missing.
     * TDD: Red phase for email validation.
     */
    @Test
    void register_shouldFail_whenEmailMissing() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user1");
        request.setPassword("password");
        // No email set
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> userService.register(request));
        assertEquals("Email is required", exception.getMessage());
        
        // Verify no database interactions occurred
        verify(userRepository, never()).existsByUsername(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test that registration fails when username is null.
     * TDD: Edge case testing for null values.
     */
    @Test
    void register_shouldFail_whenUsernameIsNull() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername(null);
        request.setPassword("password");
        request.setEmail("test@example.com");
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.register(request));
        assertEquals("Username is required", exception.getMessage());
        
        // Verify no database interactions occurred
        verify(userRepository, never()).existsByUsername(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test that registration fails when username is blank.
     * TDD: Edge case testing for blank strings.
     */
    @Test
    void register_shouldFail_whenUsernameIsBlank() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("   ");
        request.setPassword("password");
        request.setEmail("test@example.com");
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> userService.register(request));
        assertEquals("Username is required", exception.getMessage());
        
        // Verify no database interactions occurred  
        verify(userRepository, never()).existsByUsername(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
