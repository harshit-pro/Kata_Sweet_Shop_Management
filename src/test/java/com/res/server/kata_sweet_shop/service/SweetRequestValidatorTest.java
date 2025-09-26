package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.SweetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SweetRequestValidator following TDD principles.
 * Tests all validation scenarios in isolation.
 */
@ExtendWith(MockitoExtension.class)
class SweetRequestValidatorTest {

    @InjectMocks
    private SweetRequestValidator validator;

    @Test
    void validate_shouldPass_whenAllFieldsValid() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Valid Sweet");
        request.setCategory("Candy");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(10);

        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void validate_shouldFail_whenNameIsNull() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName(null);
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(10);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(request));
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    void validate_shouldFail_whenNameIsBlank() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("   ");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(10);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(request));
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    void validate_shouldFail_whenPriceIsNegative() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setPrice(new BigDecimal("-1.00"));
        request.setQuantity(10);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(request));
        assertEquals("Price must be positive", exception.getMessage());
    }

    @Test
    void validate_shouldPass_whenPriceIsNull() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setPrice(null);
        request.setQuantity(10);

        // Act & Assert - Should not throw exception for null price
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void validate_shouldFail_whenQuantityIsNegative() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(-5);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(request));
        assertEquals("Quantity must be non-negative", exception.getMessage());
    }

    @Test
    void validate_shouldPass_whenQuantityIsZero() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(0);

        // Act & Assert - Zero quantity should be allowed
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void validate_shouldPass_whenQuantityIsNull() {
        // Arrange
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(null);

        // Act & Assert - Should not throw exception for null quantity
        assertDoesNotThrow(() -> validator.validate(request));
    }
}