package wisdom.intern.task2.service;

import wisdom.intern.task2.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto getProductById(Integer ProductId);
    List<ProductDto> getAllProducts();
    ProductDto updateProduct(Integer ProductId, ProductDto updatedProductDto);
    void deleteProduct(Integer ProductId);
    List<ProductDto> getProductByCategory(Integer categoryId, int page, int size);
    ProductDto addProductToCategory(Integer categoryId, ProductDto productDto);
    ProductDto updateProductCategory(Integer productId, Integer categoryId);
    List<ProductDto> getProductByPage(int page, int size);
}
