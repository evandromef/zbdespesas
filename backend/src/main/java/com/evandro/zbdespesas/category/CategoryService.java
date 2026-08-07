package com.evandro.zbdespesas.category;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.evandro.zbdespesas.category.exception.CategoryNotFoundException;

@Service
public class CategoryService {

    private static final List<Category> CATEGORIES = new ArrayList<>(List.of(
        Category.create("Groceries"),
        Category.create("Utilities")
    ));

    public List<Category> listCategories() {
        return CATEGORIES;
    }

    public Category createCategory(String name) {
        Category newCategory = Category.create(name);
        CATEGORIES.add(newCategory);
        return newCategory;
    }

    public Category updateCategory(UUID id, String name) {
        Category existingCategory = findCategoryById(id);
        existingCategory.rename(name);
        return existingCategory;
    }

    public void deleteCategory(UUID id) {
        Category category = findCategoryById(id);
        CATEGORIES.remove(category);
    }

    public Category findCategoryById(UUID id) {
        return CATEGORIES.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new CategoryNotFoundException(id));
    }

}