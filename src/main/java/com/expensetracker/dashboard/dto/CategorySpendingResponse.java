package com.expensetracker.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class CategorySpendingResponse {

    private UUID categoryId;

    private String categoryName;

    private BigDecimal amount;
}