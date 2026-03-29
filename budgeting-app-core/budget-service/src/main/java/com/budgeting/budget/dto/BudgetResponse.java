package com.budgeting.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetResponse {
    private String id;
    private String userId;
    private String name;
    private double monthlyIncome;
    private Map<String, Double> categoryLimits;
    private Instant createdAt;
    private Instant updatedAt;
}
