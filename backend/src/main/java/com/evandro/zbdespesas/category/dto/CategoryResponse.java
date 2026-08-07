package com.evandro.zbdespesas.category.dto;

import java.util.UUID;

import com.evandro.zbdespesas.category.Category;

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
