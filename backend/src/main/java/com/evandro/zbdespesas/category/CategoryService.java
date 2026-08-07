package com.evandro.zbdespesas.category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final List<Category> CATEGORIES = new ArrayList<>(List.of(
        new Category(UUID.randomUUID().toString(), "Groceries"),
        new Category(UUID.randomUUID().toString(), "Utilities")
    ));

    public List<Category> listCategories() {
        return CATEGORIES;
    }

    public Category createCategory(Category category) {
        CategoryValidation.validateCategoryName(category.getName());
        category.setId(UUID.randomUUID().toString());
        CATEGORIES.add(category);
        return category;
    }

    public Category updateCategory(Category category) {
        CategoryValidation.validateCategory(category);
        Category existingCategory = findCategoryById(category.getId());
        existingCategory.setName(category.getName());
        return existingCategory;
    }

    public Category findCategoryById(String id) {
        return CATEGORIES.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }

    public void deleteCategory(String id) {
        CategoryValidation.validateCategoryId(id);
        Category category = findCategoryById(id);
        CATEGORIES.remove(category);
    }

}