package com.store.model.dto.user;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}