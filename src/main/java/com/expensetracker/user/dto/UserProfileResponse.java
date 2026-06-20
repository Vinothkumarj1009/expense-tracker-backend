package com.expensetracker.user.dto;

import com.expensetracker.user.entity.UserRole;
import com.expensetracker.user.entity.UserStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserProfileResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private UserRole role;

    private UserStatus status;
}