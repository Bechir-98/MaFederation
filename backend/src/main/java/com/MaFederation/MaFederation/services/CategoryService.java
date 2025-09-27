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
    private  final LogsService logsService;
    private final AuthUtils authUtils ;

    public CategoryService(CategoryRepository categoryrepository, CategoryMapper categorymapper, final LogsService logsService, final AuthUtils authUtils
    ) {
        this.categoryrepository = categoryrepository;
        this.categorymapper = categorymapper;
        this.logsService = logsService;
        this.authUtils = authUtils;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categorymapper.toEntity(categoryDTO);
        String user= authUtils.getCurrentUserId();
        logsService.log("Category Creation",user);
        return categoryrepository.save(category);
    }

    public List<CategoryDTO> loadAllCategories() {
        return categoryrepository.findAll()
            .stream()
            .map(categorymapper::toDto)
            .toList();
    }

    /** Get categories by a list of IDs, return DTOs */
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

    public Category updateCategory(Integer id, CategoryDTO categoryDTO) {
    return categoryrepository.findById(id)
        .map(existingCategory -> {
            categorymapper.updateEntityFromDto(categoryDTO, existingCategory);
            String user= authUtils.getCurrentUserId();
            logsService.log("Category Update",user);
            return categoryrepository.save(existingCategory);
        })
        .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
}

    /** Delete category by ID */
    public void deleteCategory(Integer id) {
        if (!categoryrepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        String user= authUtils.getCurrentUserId();
        logsService.log("Category deletion",user);
        categoryrepository.deleteById(id);
    }
}
