package com.zbdespesas.category.dto;

import java.util.UUID;

import com.zbdespesas.category.Category;

public record CategoryResponse(
    UUID id,
    String name
) {
   public static CategoryResponse from(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName()
        );
    } 
}
