package com.zbdespesas.category.exception;

import java.util.UUID;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID id) {
        super("Categoria não encontrada: " + id);
    }
}