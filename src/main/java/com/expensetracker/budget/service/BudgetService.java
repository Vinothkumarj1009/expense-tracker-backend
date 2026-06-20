package com.expensetracker.budget.service;

import com.expensetracker.budget.dto.BudgetResponse;
import com.expensetracker.budget.dto.CreateBudgetRequest;
import com.expensetracker.budget.entity.Budget;
import com.expensetracker.budget.repository.BudgetRepository;
import com.expensetracker.category.entity.Category;
import com.expensetracker.category.repository.CategoryRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final CategoryRepository categoryRepository;

    private final UserService userService;

    public BudgetResponse createBudget(
            CreateBudgetRequest request
    ) {

        User user =
                userService.getAuthenticatedUser();

        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Category not found"
                                )
                        );

        Budget budget = new Budget();

        budget.setAmount(
                request.getAmount()
        );

        budget.setMonth(
                request.getMonth()
        );

        budget.setYear(
                request.getYear()
        );

        budget.setCategory(category);

        budget.setUser(user);

        Budget savedBudget =
                budgetRepository.save(budget);

        return mapToResponse(savedBudget);
    }

    @Transactional(readOnly = true)
    public List<BudgetResponse> getBudgets() {

        User user =
                userService.getAuthenticatedUser();

        return budgetRepository
                .findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BudgetResponse getBudgetById(
            UUID budgetId
    ) {

        Budget budget =
                getOwnedBudget(budgetId);

        return mapToResponse(budget);
    }

    public void deleteBudget(
            UUID budgetId
    ) {

        Budget budget =
                getOwnedBudget(budgetId);

        budgetRepository.delete(budget);
    }

    private Budget getOwnedBudget(
            UUID budgetId
    ) {

        User user =
                userService.getAuthenticatedUser();

        return budgetRepository
                .findByIdAndUser(
                        budgetId,
                        user
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Budget not found"
                        )
                );
    }

    private BudgetResponse mapToResponse(
            Budget budget
    ) {

        return BudgetResponse.builder()
                .id(budget.getId())
                .amount(budget.getAmount())
                .month(budget.getMonth())
                .year(budget.getYear())
                .categoryId(
                        budget.getCategory().getId()
                )
                .categoryName(
                        budget.getCategory().getName()
                )
                .createdAt(
                        budget.getCreatedAt()
                )
                .updatedAt(
                        budget.getUpdatedAt()
                )
                .build();
    }
}