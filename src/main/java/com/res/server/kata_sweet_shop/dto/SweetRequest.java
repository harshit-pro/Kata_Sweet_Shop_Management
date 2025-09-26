package com.res.server.kata_sweet_shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a Sweet entity via API requests.
 * Includes all required fields for multipart and JSON handling.
 */
@Data
@NoArgsConstructor
public class SweetRequest {
    /** Sweet category (e.g., Chocolate, Candy) */
    private String category;
    /** Sweet name */
    @NotBlank(message = "Name is required")
    private String name;
    /** Sweet price */
    @NotNull(message = "Price is required")
    private BigDecimal price;
    /** Quantity in stock */
    private Integer quantity;
    /** Image URL for the sweet (set after upload) */
    private String imageUrl;
}
