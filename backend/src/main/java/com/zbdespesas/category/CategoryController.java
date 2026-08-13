package com.zbdespesas.category;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zbdespesas.category.dto.CategoryRequest;
import com.zbdespesas.category.dto.CategoryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")  
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list() {
        List<CategoryResponse> categories = categoryService.listCategories().stream()
            .map(CategoryResponse::from)
            .toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable UUID id) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(CategoryResponse.from(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category savedCategory = categoryService.createCategory(categoryRequest.name());
        return ResponseEntity
                    .created(URI.create("/categorias/" + savedCategory.getId()))
                    .body(CategoryResponse.from(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
        @PathVariable UUID id,
        @Valid @RequestBody CategoryRequest categoryReq
    ) {
        Category updatedCategory = categoryService.updateCategory(id, categoryReq.name());
        return ResponseEntity.ok(CategoryResponse.from(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
