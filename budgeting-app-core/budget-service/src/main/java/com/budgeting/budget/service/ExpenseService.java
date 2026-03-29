package com.budgeting.budget.service;

import com.budgeting.budget.dto.CreateExpenseRequest;
import com.budgeting.budget.dto.ExpenseResponse;
import com.budgeting.budget.dto.UpdateExpenseRequest;
import com.budgeting.budget.exception.BudgetNotFoundException;
import com.budgeting.budget.exception.ExpenseNotFoundException;
import com.budgeting.budget.model.Budget;
import com.budgeting.budget.model.Expense;
import com.budgeting.budget.repository.BudgetRepository;
import com.budgeting.budget.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;

    public ExpenseResponse createExpense(String userId, String budgetId, CreateExpenseRequest request) {
        verifyBudgetOwnership(userId, budgetId);

        Expense expense = Expense.builder()
                .userId(userId)
                .budgetId(budgetId)
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .build();

        return toResponse(expenseRepository.save(expense));
    }

    public List<ExpenseResponse> getExpensesForBudget(String userId, String budgetId) {
        verifyBudgetOwnership(userId, budgetId);
        return expenseRepository.findByBudgetId(budgetId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ExpenseResponse getExpenseById(String userId, String budgetId, String expenseId) {
        verifyBudgetOwnership(userId, budgetId);
        Expense expense = findExpenseInBudget(budgetId, expenseId);
        return toResponse(expense);
    }

    public ExpenseResponse updateExpense(String userId, String budgetId, String expenseId, UpdateExpenseRequest request) {
        verifyBudgetOwnership(userId, budgetId);
        Expense expense = findExpenseInBudget(budgetId, expenseId);

        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());

        return toResponse(expenseRepository.save(expense));
    }

    public void deleteExpense(String userId, String budgetId, String expenseId) {
        verifyBudgetOwnership(userId, budgetId);
        Expense expense = findExpenseInBudget(budgetId, expenseId);
        expenseRepository.delete(expense);
    }

    private void verifyBudgetOwnership(String userId, String budgetId) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found: " + budgetId));
        if (!budget.getUserId().equals(userId)) {
            throw new BudgetNotFoundException("Budget not found: " + budgetId);
        }
    }

    private Expense findExpenseInBudget(String budgetId, String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found: " + expenseId));
        if (!expense.getBudgetId().equals(budgetId)) {
            throw new ExpenseNotFoundException("Expense not found: " + expenseId);
        }
        return expense;
    }

    private ExpenseResponse toResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .budgetId(expense.getBudgetId())
                .userId(expense.getUserId())
                .category(expense.getCategory())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .createdAt(expense.getCreatedAt())
                .build();
    }
}
