package com.expensetracker.category.dto;

import com.expensetracker.category.entity.CategoryType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CategoryResponse {

    private UUID id;

    private String name;

    private CategoryType type;
}