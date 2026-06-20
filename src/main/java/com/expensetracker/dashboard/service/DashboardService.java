package com.expensetracker.dashboard.service;

import com.expensetracker.budget.entity.Budget;
import com.expensetracker.budget.repository.BudgetRepository;
import com.expensetracker.dashboard.dto.CategorySpendingResponse;
import com.expensetracker.dashboard.dto.DashboardResponse;
import com.expensetracker.dashboard.dto.RecentExpenseResponse;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseRepository expenseRepository;

    private final BudgetRepository budgetRepository;

    private final UserService userService;

    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {

        User user =
                userService.getAuthenticatedUser();

        int month =
                LocalDate.now().getMonthValue();

        int year =
                LocalDate.now().getYear();

        List<Expense> expenses =
                expenseRepository.findByUser(user);

        BigDecimal totalExpenses =
                expenses.stream()
                        .map(Expense::getAmount)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        List<Budget> budgets =
                budgetRepository.findByUserAndMonthAndYear(
                        user,
                        month,
                        year
                );

        BigDecimal totalBudget =
                budgets.stream()
                        .map(Budget::getAmount)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal remainingBudget =
                totalBudget.subtract(
                        totalExpenses
                );

        List<CategorySpendingResponse> categories =
                expenseRepository
                        .getCategorySpending(user)
                        .stream()
                        .limit(5)
                        .map(row ->
                                CategorySpendingResponse
                                        .builder()
                                        .categoryId(
                                                (java.util.UUID) row[0]
                                        )
                                        .categoryName(
                                                (String) row[1]
                                        )
                                        .amount(
                                                (BigDecimal) row[2]
                                        )
                                        .build()
                        )
                        .toList();

        List<RecentExpenseResponse> recentExpenses =
                expenseRepository
                        .findTop5ByUserOrderByCreatedAtDesc(
                                user
                        )
                        .stream()
                        .map(expense ->
                                RecentExpenseResponse
                                        .builder()
                                        .id(expense.getId())
                                        .title(expense.getTitle())
                                        .amount(expense.getAmount())
                                        .categoryName(
                                                expense.getCategory().getName()
                                        )
                                        .createdAt(
                                                expense.getCreatedAt()
                                        )
                                        .build()
                        )
                        .toList();

        return DashboardResponse.builder()
                .totalExpenses(totalExpenses)
                .totalBudget(totalBudget)
                .remainingBudget(remainingBudget)
                .expenseCount(
                        (long) expenses.size()
                )
                .topCategories(categories)
                .recentExpenses(recentExpenses)
                .build();
    }
}