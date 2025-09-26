package com.res.server.kata_sweet_shop.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SweetResponse {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl; // add this

}
