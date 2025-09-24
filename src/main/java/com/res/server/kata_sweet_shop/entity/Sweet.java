package com.res.server.kata_sweet_shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private Integer quantity=0;

    @Version // For optimistic locking means that this field will be used to track changes to the entity
    // and prevent concurrent updates from overwriting each other.
    // for example, if two users try to update the same Sweet entity at the same time,
    // the version field will ensure that only one of the updates is applied,
    // and the other user will receive an error indicating that the entity has been modified by another transaction.
    private Long version;

}
