package wisdom.intern.task2.mapper;

import wisdom.intern.task2.dto.ProductDto;
import wisdom.intern.task2.entity.Product;

public class ProductMapper {
    public static ProductDto maptoProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getCategoryId()
        );
    }
    public static Product maptoProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        // Category sẽ được xử lý trong service, không set trực tiếp từ DTO
        return product;
    }
}
