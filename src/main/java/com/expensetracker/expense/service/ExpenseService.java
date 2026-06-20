package com.expensetracker.expense.service;

import com.expensetracker.category.entity.Category;
import com.expensetracker.category.repository.CategoryRepository;
import com.expensetracker.expense.dto.CreateExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.dto.UpdateExpenseRequest;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final UserService userService;

    private final CategoryRepository categoryRepository;

    public ExpenseResponse createExpense(
            CreateExpenseRequest request
    ) {

        User user =
                userService.getAuthenticatedUser();

        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException("Category not found")
                        );

        Expense expense = new Expense();

        expense.setCategory(category);
        expense.setTitle(request.getTitle());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setUser(user);

        Expense savedExpense =
                expenseRepository.save(expense);

        return mapToResponse(savedExpense);
    }

    @Transactional(readOnly = true)
    public Page<ExpenseResponse> getExpenses(
            int page,
            int size
    ) {

        User user =
                userService.getAuthenticatedUser();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.DESC,
                                "createdAt"
                        )
                );

        return expenseRepository
                .findByUser(
                        user,
                        pageable
                )
                .map(this::mapToResponse);
    }

    public ExpenseResponse updateExpense(
            UUID expenseId,
            UpdateExpenseRequest request
    ) {

        Expense expense =
                getOwnedExpense(expenseId);

        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Category not found"
                                )
                        );

        expense.setTitle(request.getTitle());

        expense.setDescription(
                request.getDescription()
        );

        expense.setAmount(
                request.getAmount()
        );

        expense.setCategory(category);

        Expense savedExpense =
                expenseRepository.save(expense);

        return mapToResponse(savedExpense);
    }

    public void deleteExpense(
            UUID expenseId
    ) {

        Expense expense =
                getOwnedExpense(expenseId);

        expenseRepository.delete(expense);
    }

    private Expense getOwnedExpense(
            UUID expenseId
    ) {

        User user =
                userService.getAuthenticatedUser();

        return expenseRepository
                .findByIdAndUser(
                        expenseId,
                        user
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Expense not found"
                        )
                );
    }

    private ExpenseResponse mapToResponse(
            Expense expense
    ) {

        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .categoryId(
                        expense.getCategory().getId()
                )
                .categoryName(
                        expense.getCategory().getName()
                )
                .build();
    }

    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(
            UUID expenseId
    ) {

        Expense expense =
                getOwnedExpense(expenseId);

        return mapToResponse(expense);
    }
}