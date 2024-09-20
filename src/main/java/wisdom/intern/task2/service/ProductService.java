package wisdom.intern.task2.service;

import org.springframework.data.domain.Pageable;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.entity.Product;

import java.util.List;

public interface ProductService {
    // Gộp tạo và cập nhật Product
    Product saveOrUpdateProduct(Integer productId, Product product);
    Product getProductById(Integer ProductId);
    List<Product> getAllProducts(Pageable pageable);
    void deleteProduct(Integer ProductId);
    List<Product> getProductByCategory(Integer categoryId, int page, int size);
    Product addProductToCategory(Integer categoryId, Product product);
    Product updateProductCategory(Integer productId, Integer categoryId);
    List<Product> getProductByPage(int page, int size);
}
