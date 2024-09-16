package wisdom.intern.task2.repository;

import wisdom.intern.task2.entity.Product;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategory_CategoryId(@NonNull Integer categoryId, @NonNull Pageable pageable);
}
