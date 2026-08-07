package com.evandro.zbdespesas.category.dto;

import com.evandro.zbdespesas.category.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(    
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(
        min = Category.NAME_MIN_LENGTH, 
        max = Category.NAME_MAX_LENGTH, 
        message = "O nome da categoria deve ter entre " + Category.NAME_MIN_LENGTH + " e " + Category.NAME_MAX_LENGTH + " caracteres"
    )
    String name
) {
    public CategoryRequest {
        if(name != null) {
            name = name.strip();
        }
    }
}
