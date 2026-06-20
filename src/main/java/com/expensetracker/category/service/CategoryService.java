package com.expensetracker.category.service;

import com.expensetracker.category.dto.CategoryResponse;
import com.expensetracker.category.dto.CreateCategoryRequest;
import com.expensetracker.category.entity.Category;
import com.expensetracker.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

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
}