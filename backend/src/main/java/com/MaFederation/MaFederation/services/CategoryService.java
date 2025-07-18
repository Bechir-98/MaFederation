package com.MaFederation.MaFederation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.CategoryDTO;
import com.MaFederation.MaFederation.mappers.CategoryMapper;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryrepository;
    private final CategoryMapper categorymapper;

    public CategoryService(CategoryRepository categoryrepository, CategoryMapper categorymapper) {
        this.categoryrepository = categoryrepository;
        this.categorymapper = categorymapper;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categorymapper.toEntity(categoryDTO);
        // Persist the new category entity
        return categoryrepository.save(category);
    }

    public List<CategoryDTO> loadAllCategories() {
        return categoryrepository.findAll()
            .stream()
            .map(categorymapper::toDto)
            .toList();
    }
}
