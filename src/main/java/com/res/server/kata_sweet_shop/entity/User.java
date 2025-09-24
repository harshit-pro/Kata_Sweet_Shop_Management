package com.res.server.kata_sweet_shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;

    private String password; // it stored as a hashed value
    @ElementCollection(fetch = FetchType.EAGER) // EAGER means that roles will be loaded immediately with the user entity
    // LAZY means that roles will be loaded only when they are accessed for the first time
    // @ElementCollection is used to define a collection of basic or embeddable types means that the collection will be stored in a separate table
    // and will be mapped to the entity using a foreign key
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles; // e.g., ROLE_USER, ROLE_ADMIN

}
