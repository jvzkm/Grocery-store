package com.store.service.impl;

import com.store.dao.UserRepository;
import com.store.model.dto.user.AdminRegisterDto;
import com.store.model.dto.user.LoginUserDto;
import com.store.model.dto.user.RegisterUserDto;
import com.store.model.security.User;
import com.store.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.store.model.security.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        setRole(input, user);

        return userRepository.save(user);
    }

    private static void setRole(RegisterUserDto input, User user) {
        if (input instanceof AdminRegisterDto dto) {
            user.setRole(dto.getRole());
        } else {
            user.setRole(USER);
        }
    }

    @Override
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}