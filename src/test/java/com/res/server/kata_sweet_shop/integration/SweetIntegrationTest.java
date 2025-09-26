package com.res.server.kata_sweet_shop.integration;

import com.res.server.kata_sweet_shop.entity.Sweet;
import com.res.server.kata_sweet_shop.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
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

    @MockBean
    private Uploader uploader;

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
        
        // Mock the uploader and its methods
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(dummyResult);
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
                .file(imagePart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    /**
     * Integration test: Successfully add sweet with image using mocked Cloudinary.  
     * This test demonstrates the proper mocking approach for external dependencies.
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddSweetWithImage_shouldSucceedWithMock() throws Exception {
        String sweetJson = "{" +
                "\"name\": \"Mocked Sweet\"," +
                "\"category\": \"Candy\"," +
                "\"price\": 3.99," +
                "\"quantity\": 15" +
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
                .andExpect(status().isOk())
                .andDo(result -> {
                    // Verify that the response contains the expected image URL from our mock
                    String responseBody = result.getResponse().getContentAsString();
                    assertTrue(responseBody.contains("http://dummy.com/image.jpg"));
                });
        
        // Verify that Cloudinary was called as expected
        verify(uploader, times(1)).upload(any(byte[].class), anyMap());
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
        // Optionally, check for error details in the response body
    }
}
