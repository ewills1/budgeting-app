package com.budgeting.category.repository;

import com.budgeting.category.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findByUserId(String userId);

    Optional<Category> findByUserIdAndName(String userId, String name);
}
