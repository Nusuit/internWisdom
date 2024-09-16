package wisdom.intern.task2.controller;

import com.wisdom.task1.dto.CategoryDto;
import com.wisdom.task1.dto.ProductDto;
import com.wisdom.task1.service.CategoryService;
import com.wisdom.task1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private ProductService productService;

    // Build Add Category REST API
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto saveCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
    }

    // Build Get Category REST API
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getAllCategoryById(@PathVariable("id") Integer categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    // Build get all Categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Build Update Categories REST API
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Integer categoryId,
                                                      @RequestBody CategoryDto updatedCategory) {
        CategoryDto categoryDto = categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(categoryDto);
    }

    // Build Delete Category REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted");
    }

    // Build Get All Products By Category With Pagination
    @GetMapping("{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable("categoryId") Integer categoryId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        List<ProductDto> products = productService.getProductByCategory(categoryId, page, size);
        return ResponseEntity.ok(products);
    }


    // Build Add A Product To A Specific Category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> addProductToCategory(@PathVariable("categoryId") Integer categoryId,
                                                           @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.addProductToCategory(categoryId, productDto);
        return ResponseEntity.ok(createdProduct);
    }

}
