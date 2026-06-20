package com.expensetracker.analytics.service;

import com.expensetracker.analytics.dto.*;
import com.expensetracker.budget.entity.Budget;
import com.expensetracker.budget.repository.BudgetRepository;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;

    private final UserService userService;

    private final BudgetRepository budgetRepository;

    @Transactional(readOnly = true)
    public List<MonthlyTrendResponse> getMonthlyTrend(
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .getMonthlyTrend(
                        user,
                        year
                )
                .stream()
                .map(row -> new MonthlyTrendResponse(
                        (Integer) row[0],
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TopCategoryResponse> getTopCategories() {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .getTopCategories(user)
                .stream()
                .map(row -> new TopCategoryResponse(
                        (UUID) row[0],
                        (String) row[1],
                        (BigDecimal) row[2]
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BiggestExpenseResponse> getBiggestExpenses(
            Integer limit
    ) {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .findByUserOrderByAmountDesc(
                        user,
                        PageRequest.of(0, limit)
                )
                .stream()
                .map(expense ->
                        new BiggestExpenseResponse(
                                expense.getId(),
                                expense.getTitle(),
                                expense.getCategory().getName(),
                                expense.getAmount(),
                                expense.getCreatedAt()
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryTrendResponse> getCategoryTrend(
            UUID categoryId,
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .getCategoryTrend(
                        user,
                        categoryId,
                        year
                )
                .stream()
                .map(row ->
                        new CategoryTrendResponse(
                                ((Number) row[0]).intValue(),
                                (BigDecimal) row[1]
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BudgetAlertResponse> getBudgetAlerts(
            Integer month,
            Integer year
    ) {

        User user =
                userService.getAuthenticatedUser();

        List<Budget> budgets =
                budgetRepository.findByUser(user);

        return budgets.stream()

                .filter(budget ->
                        budget.getMonth().equals(month)
                                &&
                                budget.getYear().equals(year)
                )

                .map(budget -> {

                    BigDecimal spentAmount =
                            expenseRepository
                                    .getCategorySpendingForMonth(
                                            user,
                                            month,
                                            year
                                    )
                                    .stream()

                                    .filter(row ->
                                            row[0].equals(
                                                    budget
                                                            .getCategory()
                                                            .getId()
                                            )
                                    )

                                    .map(row ->
                                            (BigDecimal) row[1]
                                    )

                                    .findFirst()

                                    .orElse(BigDecimal.ZERO);

                    BigDecimal percentageUsed =
                            spentAmount
                                    .multiply(
                                            BigDecimal.valueOf(100)
                                    )
                                    .divide(
                                            budget.getAmount(),
                                            2,
                                            java.math.RoundingMode.HALF_UP
                                    );

                    return new BudgetAlertResponse(
                            budget.getCategory().getId(),
                            budget.getCategory().getName(),
                            budget.getAmount(),
                            spentAmount,
                            percentageUsed
                    );
                })

                .filter(alert ->
                        alert.getPercentageUsed()
                                .compareTo(
                                        BigDecimal.valueOf(80)
                                ) >= 0
                )

                .toList();
    }
}