package com.expensetracker.user.controller;

import com.expensetracker.user.dto.LoginRequest;
import com.expensetracker.user.dto.LoginResponse;
import com.expensetracker.user.dto.RegisterUserRequest;
import com.expensetracker.user.dto.RegisterUserResponse;
import com.expensetracker.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public RegisterUserResponse register(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return userService.login(request);
    }
}