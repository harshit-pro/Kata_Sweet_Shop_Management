package com.res.server.kata_sweet_shop.integration;


import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class SweetIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private SweetRepository repo;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void save_and_retrieve_sweet() {
        Sweet s = new Sweet();
        s.setName("Ladoo");
        s.setCategory("Traditional");
        s.setPrice(BigDecimal.valueOf(25.0));
        s.setQuantity(10);
        Sweet saved = repo.save(s);
        assertNotNull(saved.getId());
        Sweet fetched = repo.findById(saved.getId()).orElseThrow();
        assertEquals("Ladoo", fetched.getName());
    }
}
