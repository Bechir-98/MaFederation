package com.MaFederation.MaFederation.controllers.Club;

import org.springframework.web.bind.annotation.RestController;

import com.MaFederation.MaFederation.dto.Category.CategoryDTO;
import com.MaFederation.MaFederation.model.Category;
import com.MaFederation.MaFederation.services.CategoryService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin("http://localhost:4200/")
public class CategoryController { 

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    public Category createCategory(@RequestBody CategoryDTO categorydto) {
        return this.categoryService.createCategory(categorydto);
    }

    @GetMapping("/Categories/AllCategories")
    public List<CategoryDTO> loadAllCategories() { 
        return this.categoryService.loadAllCategories();
    }


    @PostMapping("/Categories/categories/byIds")
public List<CategoryDTO> getCategoriesByIds(@RequestBody List<Integer> categoryIds) {
    return categoryService.getCategoriesByIds(categoryIds);
}




}
