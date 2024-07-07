package com.store.rest.controllers;

import com.store.dao.UserRepository;
import com.store.model.dto.user.AdminRegisterDto;
import com.store.model.security.User;
import com.store.service.AuthenticationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @DeleteMapping("/get/{email}")
    public User getUser(@RequestParam @NotNull String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody AdminRegisterDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("/delete/{email}")
    public void delete(@RequestParam @NotNull String email) {
        log.info("delete user {}", email);
        var user = userRepository.findByEmail(email).orElseThrow();
        userRepository.delete(user);
    }

    @PatchMapping("/update/{email}")
    public User resetAccount(@RequestParam @NotNull String email,
                             @RequestBody AdminRegisterDto dto) {
        log.info("updating user {}", email);
        var user = userRepository.findByEmail(email).orElseThrow();
        update(dto, user);
        return userRepository.save(user);
    }

    private static void update(AdminRegisterDto dto, User user) {
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getEmail() != null) {
            user.setPassword(dto.getEmail());
        }
        if (dto.getFullName() != null) {
            user.setPassword(dto.getFullName());
        }
    }


}
