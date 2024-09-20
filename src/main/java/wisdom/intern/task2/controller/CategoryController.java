package wisdom.intern.task2.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.mapper.common.PageMapper;
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
    @Autowired
    private PageMapper pageMapper;

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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("getbyID")
    public ResponseEntity<Category> getAllCategoryById(@RequestParam("id") Integer categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // Build get all Categories REST API
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortType) {

        // Tạo Pageable thông qua PageMapper
        Pageable pageable = pageMapper.customPage(pageNo, pageSize, sortBy, sortType);

        List<Category> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    // Build Delete Category REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted");
    }

    // Build Get All Products By Category With Pagination
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("categoryId/products")
    public ResponseEntity<List<Product>> getProductByCategory(@RequestParam("categoryId") Integer categoryId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<Product> products = productService.getProductByCategory(categoryId, page, size);
        return ResponseEntity.ok(products);
    }


    // Build Add A Product To A Specific Category
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("categoryId/products")
    public ResponseEntity<Product> addProductToCategory(@RequestParam("categoryId") Integer categoryId,
                                                        @RequestBody Product product) {
        Product createdProduct = productService.addProductToCategory(categoryId, product);
        return ResponseEntity.ok(createdProduct);
    }

}
