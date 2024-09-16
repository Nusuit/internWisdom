package wisdom.intern.task2.service.impl;
import wisdom.intern.task2.dto.CategoryDto;
import wisdom.intern.task2.entity.Category;
import wisdom.intern.task2.exception.ResourceNotFoundException;
import wisdom.intern.task2.mapper.CategoryMapper;
import wisdom.intern.task2.repository.CategoryRepository;
import wisdom.intern.task2.service.CategoryService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.maptoCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.maptoCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category is not exist with id: " + categoryId));
        return CategoryMapper.maptoCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::maptoCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryDto updatedCategory) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category is not exist with id: " + categoryId)
        );
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        Category updatedCategoryObj = categoryRepository.save(category);
        return CategoryMapper.maptoCategoryDto(updatedCategoryObj);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category is not exist with id: " + categoryId)
        );
        categoryRepository.deleteById(categoryId);
    }




}
