package com.evandro.zbdespesas.category;

public class CategoryValidation {

    private CategoryValidation() {}

    public static void validateCategoryName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria é inválido");
        }
    }

    public static void validateCategoryId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID da categoria é inválido");
        }
    }

    public static void validateCategory(Category category) {
        validateCategoryName(category.getName());
        validateCategoryId(category.getId());
    }
}
