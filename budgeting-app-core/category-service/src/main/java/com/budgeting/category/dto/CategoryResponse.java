package com.budgeting.category.dto;

import com.budgeting.category.model.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private String id;
    private String userId;
    private String name;
    private CategoryType type;
    private String colour;
    private BigDecimal monthlyBudget;
    private Instant createdAt;
    private Instant updatedAt;
}
