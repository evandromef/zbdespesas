package com.zbdespesas.category;

import java.util.Objects;
import java.util.UUID;

import com.zbdespesas.category.exception.InvalidCategoryException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 50;

    private UUID id;
    private String name;

    private Category(UUID id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = normalizeName(name);
    }

    public static Category create(String name) {
        return new Category(UUID.randomUUID(), name);
    }

    public void rename(String name) {
        this.name = normalizeName(name);
    }

    private static String normalizeName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidCategoryException("O nome da categoria é obrigatório");
        }

        name = name.strip();
        if (name.length() < NAME_MIN_LENGTH || name.length() > NAME_MAX_LENGTH) {
            throw new InvalidCategoryException(
                "O nome da categoria deve ter entre " + NAME_MIN_LENGTH + " e " + NAME_MAX_LENGTH + " caracteres"
            );
        }

        return name;
    }
}
