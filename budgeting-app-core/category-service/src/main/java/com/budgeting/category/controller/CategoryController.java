package com.budgeting.category.controller;

import com.budgeting.category.dto.CategoryResponse;
import com.budgeting.category.dto.CreateCategoryRequest;
import com.budgeting.category.dto.UpdateCategoryRequest;
import com.budgeting.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(categoryService.getCategoriesForUser(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        return ResponseEntity.ok(categoryService.getCategoryById(userDetails.getUsername(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @RequestBody UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(userDetails.getUsername(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id) {
        categoryService.deleteCategory(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
