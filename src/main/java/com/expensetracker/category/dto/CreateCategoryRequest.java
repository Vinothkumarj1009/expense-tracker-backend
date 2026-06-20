package com.expensetracker.category.dto;

import com.expensetracker.category.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {

    @NotBlank
    private String name;

    @NotNull
    private CategoryType type;
}