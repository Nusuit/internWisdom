package wisdom.intern.task2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.service.CategoryService;
import wisdom.intern.task2.service.ProductService;
import wisdom.intern.task2.entity.Product;
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
    // Chỉ cho phép role ADMIN tạo category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> createOrUpdateCategory(@RequestParam(required = false) Integer id,
                                                           @RequestBody Category category) {
        // Gọi phương thức saveOrUpdateCategory từ service
        Category savedCategory = categoryService.saveOrUpdateCategory(id, category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Build Get Category REST API
    @GetMapping("getbyID")
    public ResponseEntity<Category> getAllCategoryById(@RequestParam("id") Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // Build get all Categories REST API
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortType) {

        List<Category> categories = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortType);
        return ResponseEntity.ok(categories);
    }

    // Build Delete Category REST API
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted");
    }

    // Build Get All Products By Category With Pagination
    @GetMapping("categoryId/products")
    public ResponseEntity<List<Product>> getProductByCategory(@RequestParam("categoryId") Integer categoryId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<Product> products = productService.getProductByCategory(categoryId, page, size);
        return ResponseEntity.ok(products);
    }


    // Build Add A Product To A Specific Category
    @PostMapping("categoryId/products")
    public ResponseEntity<Product> addProductToCategory(@RequestParam("categoryId") Integer categoryId,
                                                        @RequestBody Product product) {
        Product createdProduct = productService.addProductToCategory(categoryId, product);
        return ResponseEntity.ok(createdProduct);
    }

}
