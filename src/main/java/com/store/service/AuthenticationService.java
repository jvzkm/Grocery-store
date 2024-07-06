package com.store.service;

import com.store.model.dto.user.LoginUserDto;
import com.store.model.dto.user.RegisterUserDto;
import com.store.model.security.User;

public interface AuthenticationService {
    User signup(RegisterUserDto input);

    User authenticate(LoginUserDto input);
}
