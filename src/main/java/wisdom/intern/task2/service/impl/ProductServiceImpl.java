package wisdom.intern.task2.service.impl;

import lombok.NoArgsConstructor;
import wisdom.intern.task2.entity.Product;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.repository.ProductRepository;
import wisdom.intern.task2.repository.CategoryRepository;
import wisdom.intern.task2.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wisdom.intern.task2.mapper.common.PageMapper;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PageMapper pageMapper;

    // Gộp tạo và cập nhật Product
    @Override
    public Product saveOrUpdateProduct(Integer productId, Product product) {
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        if (productId != null) {
            // Cập nhật sản phẩm
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());

            // Tìm category theo category_Id trong Product
            Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with id " + product.getCategory().getCategoryId() + " not found"));

            // Gán category tìm được cho product
            existingProduct.setCategory(category);

            return productRepository.save(existingProduct);
        } else {
            // Tạo mới sản phẩm
            Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + product.getCategory().getCategoryId()));

            // Gán Category vào Product
            product.setCategory(category);

            // Lưu Product vào database
            return productRepository.save(product);
        }
    }



    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + productId + " not found"));
    }

    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getProductByCategory(Integer categoryId, int page, int size) {
        Pageable pageable = pageMapper.customPage(page, size, "name", "asc");
        Page<Product> productsPage = productRepository.findByCategory_CategoryId(categoryId, pageable);
        return productsPage.getContent();
    }


    @Override
    public Product addProductToCategory(Integer categoryId, Product product) {
        // Find category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Set the category for the product
        product.setCategory(category);

        // Save the product in the database
        return productRepository.save(product);
    }

    @Override
    public Product updateProductCategory(Integer productId, Integer categoryId) {
        // Find the product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Find the category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Update the product's category
        product.setCategory(category);

        // Save the updated product
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProductByPage(int page, int size) {
        Pageable pageable = pageMapper.customPage(page, size, "name", "asc");
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.getContent();
    }
}
