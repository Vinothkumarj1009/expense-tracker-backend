package com.expensetracker.analytics.controller;

import com.expensetracker.analytics.dto.*;
import com.expensetracker.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/monthly-trend")
    public List<MonthlyTrendResponse> getMonthlyTrend(
            @RequestParam Integer year
    ) {

        return analyticsService.getMonthlyTrend(
                year
        );
    }

    @GetMapping("/top-categories")
    public List<TopCategoryResponse> getTopCategories() {

        return analyticsService.getTopCategories();
    }

    @GetMapping("/biggest-expenses")
    public List<BiggestExpenseResponse> getBiggestExpenses(
            @RequestParam(defaultValue = "10")
            Integer limit
    ) {

        return analyticsService
                .getBiggestExpenses(limit);
    }

    @GetMapping("/category-trend")
    public List<CategoryTrendResponse> getCategoryTrend(
            @RequestParam UUID categoryId,
            @RequestParam Integer year
    ) {

        return analyticsService.getCategoryTrend(
                categoryId,
                year
        );
    }

    @GetMapping("/budget-alerts")
    public List<BudgetAlertResponse> getBudgetAlerts(
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {

        return analyticsService.getBudgetAlerts(
                month,
                year
        );
    }
}