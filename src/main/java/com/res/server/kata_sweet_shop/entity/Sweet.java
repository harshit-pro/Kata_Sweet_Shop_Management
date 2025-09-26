package com.res.server.kata_sweet_shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity representing a Sweet in the shop management system.
 * Uses optimistic locking with version control for concurrent access protection.
 */
@Getter
@Setter
@Entity
@Table(name = "sweets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String category;
    private BigDecimal price;
    
    @Builder.Default
    private Integer quantity = 0;

    /**
     * Version field for optimistic locking.
     * Ensures that concurrent updates don't overwrite each other.
     * When two users try to update the same Sweet entity simultaneously,
     * only one update will succeed and the other will receive a conflict error.
     */
    @Version
    private Long version;
    
    private String imageUrl;
}
