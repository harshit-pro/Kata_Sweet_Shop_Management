package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.SweetRequest;
import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.exception.InsufficientStockException;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SweetServiceTest {

    @Mock
    private SweetRepository sweetRepository;

    @InjectMocks
    private SweetServiceImpl sweetService;

    @Test
    void purchase_decreases_quantity() {
        // Arrange
        Sweet s = new Sweet();
        s.setId(1L);
        s.setQuantity(5);
        s.setPrice(BigDecimal.valueOf(10));

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(s));

        // Act
        sweetService.purchasesweet(1L, 2);

        // Assert
        assertEquals(3, s.getQuantity());
        verify(sweetRepository, times(1)).save(s);
    }

    @Test
    void purchaseSweet_shouldThrowException_whenQuantityInsufficient() {
        // Arrange
        Sweet sweet = new Sweet();
        sweet.setId(1L);
        sweet.setName("Ladoo");
        sweet.setQuantity(0);

        when(sweetRepository.findById(1L)).thenReturn(Optional.of(sweet));

        // Act + Assert
        assertThrows(InsufficientStockException.class, () -> {
            sweetService.purchasesweet(1L, 1);
        });
        verify(sweetRepository, never()).save(any());
    }

    @Test
    void testCreateSweetWithImageUrl() {
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setCategory("Candy");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(10);
        request.setImageUrl("http://test.com/image.jpg");
        Sweet expectedSweet = Sweet.builder()
                .name("Test Sweet")
                .category("Candy")
                .price(new BigDecimal("2.99"))
                .quantity(10)
                .imageUrl("http://test.com/image.jpg")
                .build();
        when(sweetRepository.save(any(Sweet.class))).thenReturn(expectedSweet);
        Sweet sweet = sweetService.create(request);
        assertNotNull(sweet);
        assertEquals("http://test.com/image.jpg", sweet.getImageUrl());
    }

    @Test
    void createSweet_shouldThrowException_whenNameMissing() {
        // Red: Test for missing name validation
        SweetRequest request = new SweetRequest();
        request.setCategory("Candy");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(10);
        request.setImageUrl("http://test.com/image.jpg");
        
        // Act & Assert - The service should validate and throw exception before calling repository
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> sweetService.create(request));
        assertEquals("Name is required", exception.getMessage());
        
        // Verify repository save was never called since validation failed
        verify(sweetRepository, never()).save(any(Sweet.class));
    }

    /**
     * Test that SweetService.create fails when required fields (name, price) are missing.
     * This follows TDD Red-Green-Refactor: testing validation before implementation.
     */
    @Test
    void createSweet_shouldFail_whenRequiredFieldsMissing() {
        // Red: Test case for missing required fields validation
        SweetRequest request = new SweetRequest();
        request.setCategory("Candy");
        request.setQuantity(10);
        request.setImageUrl("http://test.com/image.jpg");
        // No name, no price set
        
        // Act & Assert - Service should validate inputs before calling repository
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> sweetService.create(request));
        assertEquals("Name is required", exception.getMessage());
        
        // Verify repository was never called due to validation failure
        verify(sweetRepository, never()).save(any(Sweet.class));
    }

    /**
     * TDD Red phase: Test for negative price validation
     * This test should fail initially, driving us to implement price validation
     */
    @Test
    void createSweet_shouldFail_whenPriceIsNegative() {
        // Arrange - Create request with negative price
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setCategory("Candy");
        request.setPrice(new BigDecimal("-1.00")); // Negative price should be invalid
        request.setQuantity(10);
        
        // Act & Assert - Should throw exception for negative price
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> sweetService.create(request));
        assertEquals("Price must be positive", exception.getMessage());
        
        // Verify repository save was never called
        verify(sweetRepository, never()).save(any(Sweet.class));
    }

    /**
     * TDD Red phase: Test for zero quantity validation
     * This test ensures we validate business rules properly
     */
    @Test
    void createSweet_shouldFail_whenQuantityIsNegative() {
        // Arrange - Create request with negative quantity
        SweetRequest request = new SweetRequest();
        request.setName("Test Sweet");
        request.setCategory("Candy");
        request.setPrice(new BigDecimal("2.99"));
        request.setQuantity(-5); // Negative quantity should be invalid
        
        // Act & Assert - Should throw exception for negative quantity
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> sweetService.create(request));
        assertEquals("Quantity must be non-negative", exception.getMessage());
        
        // Verify repository save was never called
        verify(sweetRepository, never()).save(any(Sweet.class));
    }
}
