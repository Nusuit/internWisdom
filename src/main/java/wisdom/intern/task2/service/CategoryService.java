package wisdom.intern.task2.service;

import wisdom.intern.task2.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Integer categoryId);

    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(Integer categoryId, CategoryDto updatedCategory);
    void deleteCategory(Integer categoryId);



}
