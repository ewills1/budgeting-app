package com.budgeting.category.service;

import com.budgeting.category.dto.CategoryResponse;
import com.budgeting.category.dto.CreateCategoryRequest;
import com.budgeting.category.dto.UpdateCategoryRequest;
import com.budgeting.category.exception.CategoryNotFoundException;
import com.budgeting.category.exception.DuplicateCategoryException;
import com.budgeting.category.model.Category;
import com.budgeting.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(String userId, CreateCategoryRequest request) {
        categoryRepository.findByUserIdAndName(userId, request.getName()).ifPresent(existing -> {
            throw new DuplicateCategoryException(
                    "Category with name '" + request.getName() + "' already exists for this user");
        });

        Category category = Category.builder()
                .userId(userId)
                .name(request.getName())
                .type(request.getType())
                .colour(request.getColour())
                .monthlyBudget(request.getMonthlyBudget())
                .build();

        return toResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getCategoriesForUser(String userId) {
        return categoryRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(String userId, String categoryId) {
        Category category = findCategoryForUser(userId, categoryId);
        return toResponse(category);
    }

    public CategoryResponse updateCategory(String userId, String categoryId, UpdateCategoryRequest request) {
        Category category = findCategoryForUser(userId, categoryId);

        if (!category.getName().equals(request.getName())) {
            categoryRepository.findByUserIdAndName(userId, request.getName()).ifPresent(existing -> {
                throw new DuplicateCategoryException(
                        "Category with name '" + request.getName() + "' already exists for this user");
            });
        }

        Category updated = Category.builder()
                .id(category.getId())
                .userId(category.getUserId())
                .name(request.getName())
                .type(request.getType())
                .colour(request.getColour())
                .monthlyBudget(request.getMonthlyBudget())
                .createdAt(category.getCreatedAt())
                .build();

        return toResponse(categoryRepository.save(updated));
    }

    public void deleteCategory(String userId, String categoryId) {
        Category category = findCategoryForUser(userId, categoryId);
        categoryRepository.delete(category);
    }

    private Category findCategoryForUser(String userId, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + categoryId));
        if (!category.getUserId().equals(userId)) {
            throw new CategoryNotFoundException("Category not found: " + categoryId);
        }
        return category;
    }

    private CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .userId(category.getUserId())
                .name(category.getName())
                .type(category.getType())
                .colour(category.getColour())
                .monthlyBudget(category.getMonthlyBudget())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
