package com.expensetracker.expense.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class ExpenseResponse {

    private UUID id;

    private String title;

    private String description;

    private BigDecimal amount;

    private Instant createdAt;

    private Instant updatedAt;

    private UUID categoryId;

    private String categoryName;
}