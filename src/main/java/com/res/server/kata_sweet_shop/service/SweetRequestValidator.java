package com.res.server.kata_sweet_shop.service;

import com.res.server.kata_sweet_shop.dto.SweetRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Validator for SweetRequest objects following Single Responsibility Principle.
 * Centralizes all validation logic for sweet creation and updates.
 */
@Component
public class SweetRequestValidator {

    /**
     * Validates a SweetRequest according to business rules.
     * 
     * @param request The request to validate
     * @throws IllegalArgumentException if validation fails with descriptive message
     */
    public void validate(SweetRequest request) {
        validateName(request.getName());
        validatePrice(request.getPrice());
        validateQuantity(request.getQuantity());
    }

    /**
     * Validates the name field.
     * 
     * @param name The name to validate
     * @throws IllegalArgumentException if name is null or blank
     */
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
    }

    /**
     * Validates the price field.
     * 
     * @param price The price to validate
     * @throws IllegalArgumentException if price is negative
     */
    private void validatePrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    /**
     * Validates the quantity field.
     * 
     * @param quantity The quantity to validate
     * @throws IllegalArgumentException if quantity is negative
     */
    private void validateQuantity(Integer quantity) {
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
    }
}