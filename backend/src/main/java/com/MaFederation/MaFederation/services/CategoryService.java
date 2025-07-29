package com.MaFederation.MaFederation.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
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
    
        return categoryrepository.save(category);
    }



    public List<CategoryDTO> loadAllCategories() {
        return categoryrepository.findAll()
            .stream()
            .map(categorymapper::toDto)
            .toList();
    }
     /** Obtenir les catégories d’un club, renvoyées en DTO */
    
  public List<CategoryDTO> getCategoriesByIds(List<Integer> categoryIds) {
    if (categoryIds == null || categoryIds.isEmpty()) {
        return Collections.emptyList();
    }
    List<Category> categories = categoryrepository.findAllById(categoryIds);

    return categories.stream()
            .map(categorymapper::toDto)
            .collect(Collectors.toList());
}



 public List<Category> getCategoriesByIdsEntity(List<Integer> categoryIds) {
    if (categoryIds == null || categoryIds.isEmpty()) {
        return Collections.emptyList();
    }
    List<Category> categories = categoryrepository.findAllById(categoryIds);

    return categories;
}


}
