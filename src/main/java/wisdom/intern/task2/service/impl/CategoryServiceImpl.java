package wisdom.intern.task2.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.repository.CategoryRepository;
import wisdom.intern.task2.service.CategoryService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    // Gộp tạo và cập nhật Category
    @Override
    public Category saveOrUpdateCategory(Integer categoryId, Category category) {
        if (categoryId != null) {
            Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(
                    () -> new ResourceNotFoundException("Category is not exist with id: " + categoryId)
            );
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());

            // Cập nhật Category
            return categoryRepository.save(existingCategory);
        } else {
            // Tạo mới Category
            return categoryRepository.save(category);
        }
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category is not exist with id: " + categoryId));

    }

    @Override
    public List<Category> getAllCategories(Integer pageNo, Integer pageSize, String sortBy, String sortType) {
        Sort.Direction direction = sortType.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category is not exist with id: " + categoryId)
        );
        categoryRepository.deleteById(categoryId);
    }




}
