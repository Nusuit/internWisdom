package wisdom.intern.task2.service.impl;

import wisdom.intern.task2.dto.ProductDto;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.entity.Product;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.mapper.ProductMapper;
import wisdom.intern.task2.repository.ProductRepository;
import wisdom.intern.task2.repository.CategoryRepository;
import wisdom.intern.task2.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto) {
        // Tìm Category từ categoryId
        Category category = categoryRepository.findById(productDto.getCategory_Id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategory_Id()));

        // Tạo đối tượng Product từ ProductDto
        Product product = ProductMapper.maptoProduct(productDto);
        // Gán Category vào Product
        product.setCategory(category);

        // Lưu Product vào database
        Product savedProduct = productRepository.save(product);
        return ProductMapper.maptoProductDto(savedProduct);
    }


    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product with id " + productId + " not found"));
        return ProductMapper.maptoProductDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::maptoProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(Integer productId, ProductDto updatedProductDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + productId + " not found"));
        product.setName(updatedProductDto.getName());
        product.setPrice(updatedProductDto.getPrice());
        product.setDescription(updatedProductDto.getDescription());

        // Tìm category theo category_Id trong ProductDto
        Category category = categoryRepository.findById(updatedProductDto.getCategory_Id())
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + updatedProductDto.getCategory_Id() + " not found"));

        // Gán category tìm được cho product
        product.setCategory(category);

        Product updatedProductObj = productRepository.save(product);
        return ProductMapper.maptoProductDto(updatedProductObj);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductDto> getProductByCategory(Integer categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productRepository.findByCategory_CategoryId(categoryId, pageable);

        return productsPage.stream()
                .map(ProductMapper::maptoProductDto)
                .collect(Collectors.toList());
    }


    @Override
    public ProductDto addProductToCategory(Integer categoryId, ProductDto productDto) {
        // Find category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Map ProductDto to Product entity
        Product product = ProductMapper.maptoProduct(productDto);
        // Set the category for the product
        product.setCategory(category);

        // Save the product in the database
        Product savedProduct = productRepository.save(product);

        // Map the saved product back to ProductDto
        return ProductMapper.maptoProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProductCategory(Integer productId, Integer categoryId) {
        // Find the product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        // Find the category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Update the product's category
        product.setCategory(category);

        // Save the updated product
        Product updatedProduct = productRepository.save(product);

        // Return the updated product as a DTO
        return ProductMapper.maptoProductDto(updatedProduct);
    }

    @Override
    public List<ProductDto> getProductByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productRepository.findAll(pageable);

        return productsPage.stream()
                .map(ProductMapper::maptoProductDto)
                .collect(Collectors.toList());
    }
}
