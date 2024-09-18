package wisdom.intern.task2.mapper;

import wisdom.intern.task2.dto.CategoryDto;
import wisdom.intern.task2.entity.Category;

public class CategoryMapper {
    public static CategoryDto maptoCategoryDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()


        );
    }
    public static Category maptoCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getCategoryId(),
                categoryDto.getName(),
                categoryDto.getDescription()

        );
    }
}
