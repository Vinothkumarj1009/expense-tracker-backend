package com.expensetracker.user.controller;

import com.expensetracker.user.dto.UserProfileResponse;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserProfileResponse me(
            Authentication authentication
    ) {

        return userService.getCurrentUser(
                authentication
        );
    }
}