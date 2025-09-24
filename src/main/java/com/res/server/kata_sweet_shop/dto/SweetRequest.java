package com.res.server.kata_sweet_shop.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class SweetRequest {
private String category;
private String name;
private BigDecimal price;
private Integer quantity;
}
