package com.res.server.kata_sweet_shop.integration;


import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.driver-class-name=org.h2.Driver"
})
@ActiveProfiles("test")
public class SweetIntegrationTest {

    @Autowired
    private SweetRepository repo;

    @Autowired
    private MockMvc mockMvc;

    // Create a TestConfiguration that replaces the real Cloudinary bean with a mock
    @TestConfiguration
    static class CloudinaryTestConfig {
        @Bean
        @Primary
        public Cloudinary cloudinary() {
            Cloudinary mockCloudinary = mock(Cloudinary.class);
            Uploader mockUploader = mock(Uploader.class);

            try {
                Map<String, Object> result = new HashMap<>();
                result.put("url", "http://dummy.com/image.jpg");

                when(mockCloudinary.uploader()).thenReturn(mockUploader);
                when(mockUploader.upload(any(), anyMap())).thenReturn(result);
            } catch (Exception e) {
                // Handle exception
            }

            return mockCloudinary;
        }
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

    /**
     * Integration test for multipart image upload to /api/sweets/add.
     * Cloudinary is mocked to always return a dummy image URL.
     */
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
                .file(imagePart))
                .andExpect(status().isOk());
    }

    /**
     * Integration test: invalid SweetRequest should return 400 Bad Request with error details.
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addSweet_shouldReturn400_whenRequestInvalid() throws Exception {
        String sweetJson = "{" +
                "\"category\": \"Candy\"," +
                "\"quantity\": 10" +
                "}"; // missing name and price
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
                .andExpect(status().isBadRequest());
    }
}
