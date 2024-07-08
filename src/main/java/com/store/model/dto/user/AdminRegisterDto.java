package com.store.model.dto.user;

import com.store.model.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterDto extends RegisterUserDto {
    private Role role;
}
