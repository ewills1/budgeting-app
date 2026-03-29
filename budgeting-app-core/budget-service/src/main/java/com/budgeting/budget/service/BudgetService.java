package com.budgeting.budget.service;

import com.budgeting.budget.dto.BudgetResponse;
import com.budgeting.budget.dto.CreateBudgetRequest;
import com.budgeting.budget.dto.UpdateBudgetRequest;
import com.budgeting.budget.exception.BudgetNotFoundException;
import com.budgeting.budget.model.Budget;
import com.budgeting.budget.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public BudgetResponse createBudget(String userId, CreateBudgetRequest request) {
        Budget budget = Budget.builder()
                .userId(userId)
                .name(request.getName())
                .monthlyIncome(request.getMonthlyIncome())
                .categoryLimits(request.getCategoryLimits())
                .build();

        return toResponse(budgetRepository.save(budget));
    }

    public List<BudgetResponse> getBudgetsForUser(String userId) {
        return budgetRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public BudgetResponse getBudgetById(String userId, String budgetId) {
        Budget budget = findBudgetForUser(userId, budgetId);
        return toResponse(budget);
    }

    public BudgetResponse updateBudget(String userId, String budgetId, UpdateBudgetRequest request) {
        Budget budget = findBudgetForUser(userId, budgetId);

        budget.setName(request.getName());
        budget.setMonthlyIncome(request.getMonthlyIncome());
        budget.setCategoryLimits(request.getCategoryLimits());

        return toResponse(budgetRepository.save(budget));
    }

    public void deleteBudget(String userId, String budgetId) {
        Budget budget = findBudgetForUser(userId, budgetId);
        budgetRepository.delete(budget);
    }

    private Budget findBudgetForUser(String userId, String budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found: " + budgetId));
        if (!budget.getUserId().equals(userId)) {
            throw new BudgetNotFoundException("Budget not found: " + budgetId);
        }
        return budget;
    }

    private BudgetResponse toResponse(Budget budget) {
        return BudgetResponse.builder()
                .id(budget.getId())
                .userId(budget.getUserId())
                .name(budget.getName())
                .monthlyIncome(budget.getMonthlyIncome())
                .categoryLimits(budget.getCategoryLimits())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .build();
    }
}
