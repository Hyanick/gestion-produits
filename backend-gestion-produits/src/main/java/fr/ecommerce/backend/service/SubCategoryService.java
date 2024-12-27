package fr.ecommerce.backend.service;

import fr.ecommerce.backend.exceptions.ResourceNotFoundException;
import fr.ecommerce.backend.model.Category;
import fr.ecommerce.backend.model.SubCategory;
import fr.ecommerce.backend.repository.CategoryRepository;
import fr.ecommerce.backend.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public SubCategory createSubCategory(Long categoryId, SubCategory subCategory) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> getAllSubCategoriesByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
        return subCategoryRepository.findByCategory(category);
    }

    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id " + id));
    }

    public SubCategory updateSubCategory(Long id, SubCategory subCategoryDetails) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id " + id));

        subCategory.setName(subCategoryDetails.getName());
        subCategory.setDescription(subCategoryDetails.getDescription());
        return subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found with id " + id));

        subCategoryRepository.delete(subCategory);
    }
}

