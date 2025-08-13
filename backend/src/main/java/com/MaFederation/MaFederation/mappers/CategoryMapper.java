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
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setAgeMin(dto.getAgeMin());
        category.setAgeMax(dto.getAgeMax());

        return category;
    }

        public void updateEntityFromDto(CategoryDTO dto, Category entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAgeMin(dto.getAgeMin());
        entity.setAgeMax(dto.getAgeMax());
    }

}
