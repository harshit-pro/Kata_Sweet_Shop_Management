package com.res.server.kata_sweet_shop.service;

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
        sweetService.purchase(1L, 2);

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
            sweetService.purchase(1L, 1);
        });
        verify(sweetRepository, never()).save(any());
    }
}
