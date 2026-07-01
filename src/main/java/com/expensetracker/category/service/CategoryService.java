package com.expensetracker.category.service;

import com.expensetracker.budget.repository.BudgetRepository;
import com.expensetracker.category.dto.CategoryResponse;
import com.expensetracker.category.dto.CreateCategoryRequest;
import com.expensetracker.category.entity.Category;
import com.expensetracker.category.exception.CategoryInUseException;
import com.expensetracker.category.exception.CategoryNotFoundException;
import com.expensetracker.category.repository.CategoryRepository;
import com.expensetracker.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    public CategoryResponse createCategory(
            CreateCategoryRequest request
    ) {

        Category category = new Category();

        category.setName(request.getName());
        category.setType(request.getType());

        Category savedCategory =
                categoryRepository.save(category);

        return mapToResponse(savedCategory);
    }

    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CategoryResponse mapToResponse(
            Category category
    ) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
    }

    public CategoryResponse updateCategory(
            UUID id,
            CreateCategoryRequest request
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(
                                CategoryNotFoundException::new
                        );

        category.setName(request.getName());
        category.setType(request.getType());

        Category updated =
                categoryRepository.save(category);

        return mapToResponse(updated);
    }

    public void deleteCategory(
            UUID id
    ) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(
                                CategoryNotFoundException::new
                        );

        boolean usedByExpense =
                expenseRepository.existsByCategoryId(id);

        boolean usedByBudget =
                budgetRepository.existsByCategoryId(id);

        if (usedByExpense || usedByBudget) {

            throw new CategoryInUseException();
        }

        categoryRepository.delete(category);
    }
}