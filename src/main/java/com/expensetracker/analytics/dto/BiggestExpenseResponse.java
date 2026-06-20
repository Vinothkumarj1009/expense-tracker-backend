package com.expensetracker.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BiggestExpenseResponse {

    private UUID expenseId;

    private String title;

    private String categoryName;

    private BigDecimal amount;

    private Instant createdAt;
}