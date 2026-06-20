package com.expensetracker.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class RegisterUserResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private String status;

    private Instant createdAt;
}