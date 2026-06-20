package com.expensetracker.report.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class CategorySummaryResponse {

    private UUID categoryId;

    private String categoryName;

    private BigDecimal totalSpent;
}