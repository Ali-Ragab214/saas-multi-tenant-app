package ali.ragab.saas_multi_tennant_app.controllers;

import ali.ragab.saas_multi_tennant_app.requests.CategoryRequest;
import ali.ragab.saas_multi_tennant_app.responses.CategoryResponse;
import ali.ragab.saas_multi_tennant_app.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(
            @Valid
            @RequestBody final CategoryRequest request) {
        categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable("id") final String id,
            @Valid
            @RequestBody final CategoryRequest request) {
        categoryService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable("id") final String id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("id") final String id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}