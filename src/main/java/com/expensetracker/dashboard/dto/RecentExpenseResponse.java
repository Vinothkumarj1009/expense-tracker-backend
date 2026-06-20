package com.expensetracker.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class RecentExpenseResponse {

    private UUID id;

    private String title;

    private BigDecimal amount;

    private String categoryName;

    private Instant createdAt;
}