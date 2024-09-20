package wisdom.intern.task2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import wisdom.intern.task2.entity.Product;
import wisdom.intern.task2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Build Add Product REST API
    // Chỉ cho phép role ADMIN tạo product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> createOrUpdateProduct(@RequestParam(required = false) Integer id,
                                                         @RequestBody Product product) {
        // Gọi phương thức saveOrUpdateProduct từ service
        Product savedProduct = productService.saveOrUpdateProduct(id, product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Build Get Product REST API
    @GetMapping("getById")
    public ResponseEntity<Product> getAllProductById(@RequestParam("id") Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // Build Get All Products REST API
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc", required = false) String sortType) {

        List<Product> products = productService.getAllProducts(pageNo, pageSize, sortBy, sortType);
        return ResponseEntity.ok(products);
    }

    // Build Delete Product REST API
    @DeleteMapping("delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam("id") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted");
    }

    // Build Update The Category Of A Product
    @PatchMapping("category")
    public ResponseEntity<Product> updateProductCategory(@RequestParam("productId") Integer productId,
                                                         @RequestParam("categoryId") Integer categoryId) {
        Product updatedProduct = productService.updateProductCategory(productId, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    // Build Get A Paginated List Of Products
    @GetMapping("paged")
    public ResponseEntity<List<Product>> getProductByPage(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        List<Product> products = productService.getProductByPage(page, size);
        return ResponseEntity.ok(products);
    }

}
