package com.expensetracker.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TopCategoryResponse {

    private UUID categoryId;

    private String categoryName;

    private BigDecimal totalAmount;
}