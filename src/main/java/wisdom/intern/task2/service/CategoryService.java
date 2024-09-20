package wisdom.intern.task2.service;

import org.springframework.data.domain.Pageable;
import wisdom.intern.task2.entity.Category;

import java.util.List;

public interface CategoryService {
    // Gộp tạo và cập nhật Category
    Category saveOrUpdateCategory(Integer categoryId, Category category);
    Category getCategoryById(Integer categoryId);
    List<Category> getAllCategories(Pageable pageable);
    void deleteCategory(Integer categoryId);



}
