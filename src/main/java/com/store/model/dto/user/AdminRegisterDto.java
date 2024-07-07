package com.store.model.dto.user;

import com.store.model.security.Role;
import lombok.Data;

@Data
public class AdminRegisterDto extends RegisterUserDto {
    private Role role;
}
