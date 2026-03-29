package com.budgeting.category.dto;

import com.budgeting.category.model.CategoryType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCategoryRequest {
    private String name;
    private CategoryType type;
    private String colour;
    private BigDecimal monthlyBudget;
}
