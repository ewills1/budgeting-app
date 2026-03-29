package com.budgeting.category.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "categories")
@CompoundIndex(name = "userId_name_unique", def = "{'userId': 1, 'name': 1}", unique = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String id;

    private String userId;

    private String name;

    private CategoryType type;

    private String colour;

    private BigDecimal monthlyBudget;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
