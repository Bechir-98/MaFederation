package com.MaFederation.MaFederation.mappers;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
import com.MaFederation.MaFederation.model.Category;

@Service
public class CategoryMapper {

    public CategoryDTO toDto(Category category) {
        if (category == null) return null;

        return new CategoryDTO(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getAgeMin(),
            category.getAgeMax()
        );
    }

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setAgeMin(dto.ageMin());
        category.setAgeMax(dto.ageMax());

        return category;
    }
}
