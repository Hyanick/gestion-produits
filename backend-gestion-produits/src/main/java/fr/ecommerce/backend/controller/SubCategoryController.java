package fr.ecommerce.backend.controller;

import fr.ecommerce.backend.model.SubCategory;
import fr.ecommerce.backend.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping("/{categoryId}")
    public SubCategory createSubCategory(@PathVariable Long categoryId, @RequestBody SubCategory subCategory) {
        return subCategoryService.createSubCategory(categoryId, subCategory);
    }

    @GetMapping("/category/{categoryId}")
    public List<SubCategory> getAllSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return subCategoryService.getAllSubCategoriesByCategoryId(categoryId);
    }

    @GetMapping("/{id}")
    public SubCategory getSubCategoryById(@PathVariable Long id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @PutMapping("/{id}")
    public SubCategory updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategoryDetails) {
        return subCategoryService.updateSubCategory(id, subCategoryDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
    }
}

