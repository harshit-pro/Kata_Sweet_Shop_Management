package com.res.server.kata_sweet_shop.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
