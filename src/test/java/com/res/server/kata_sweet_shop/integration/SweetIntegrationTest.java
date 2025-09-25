package com.res.server.kata_sweet_shop.integration;


import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cloudinary.Cloudinary;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.HashMap;
import java.util.Map;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class SweetIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private SweetRepository repo;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setupCloudinaryMock() throws Exception {
        Map<String, Object> dummyResult = new HashMap<>();
        dummyResult.put("url", "http://dummy.com/image.jpg");
        when(cloudinary.uploader().upload(any(byte[].class), anyMap())).thenReturn(dummyResult);
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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddSweetWithImage() throws Exception {
        String sweetJson = "{" +
                "\"name\": \"Test Sweet\"," +
                "\"category\": \"Candy\"," +
                "\"price\": 2.99," +
                "\"quantity\": 10" +
                "}";
        MockMultipartFile sweetPart = new MockMultipartFile(
                "sweet", "sweet.json", "application/json", sweetJson.getBytes()
        );
        MockMultipartFile imagePart = new MockMultipartFile(
                "image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "dummy image content".getBytes()
        );
        mockMvc.perform(multipart("/api/sweets/add")
                .file(sweetPart)
                .file(imagePart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
}
