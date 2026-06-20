package com.expensetracker.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class DashboardResponse {

    private BigDecimal totalExpenses;

    private BigDecimal totalBudget;

    private BigDecimal remainingBudget;

    private Long expenseCount;

    private List<CategorySpendingResponse> topCategories;

    private List<RecentExpenseResponse> recentExpenses;
}