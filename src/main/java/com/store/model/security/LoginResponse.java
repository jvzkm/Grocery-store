package com.store.model.security;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}