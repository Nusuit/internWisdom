package wisdom.intern.task2.controller;

import wisdom.intern.task2.dto.ProductDto;
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
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto saveProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    // Build Get Product REST API
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getAllProductById(@PathVariable("id") Integer productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    // Build Get All Products REST API
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Build Update Products REST API
    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Integer productId,
                                                    @RequestBody ProductDto updatedProduct) {
        ProductDto productDto = productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok(productDto);
    }

    // Build Delete Product REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted");
    }

    // Build Update The Category Of A Product
    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ProductDto> updateProductCategory(@PathVariable("productId") Integer productId,
                                                            @PathVariable("categoryId") Integer categoryId) {
        ProductDto updatedProduct = productService.updateProductCategory(productId, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    // Build Get A Paginated List Of Products
    @GetMapping("/paged")
    public ResponseEntity<List<ProductDto>> getProductByPage(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "3") int size) {
        List<ProductDto> products = productService.getProductByPage(page, size);
        return ResponseEntity.ok(products);
    }

}
