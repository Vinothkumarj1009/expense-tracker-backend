package com.expensetracker.budget.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class BudgetResponse {

    private UUID id;

    private UUID categoryId;

    private String categoryName;

    private BigDecimal amount;

    private Integer month;

    private Integer year;

    private Instant createdAt;

    private Instant updatedAt;
}