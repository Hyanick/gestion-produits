package fr.ecommerce.backend.controller;

import fr.ecommerce.backend.model.SubCategory;
import fr.ecommerce.backend.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@Tag(name = "SubCategory", description = "SubCategory management APIs")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping("/{categoryId}")
    @Operation(summary = "Create a new subcategory", description = "Create a new subcategory with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubCategory created successfully",
                    content = @Content(schema = @Schema(implementation = SubCategory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "SubCategory not found")
    })
    public SubCategory createSubCategory(@PathVariable Long categoryId, @RequestBody SubCategory subCategory) {
        return subCategoryService.createSubCategory(categoryId, subCategory);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get all subcategories by category ID", description = "Retrieve a list of all subcategories by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of subcategories retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SubCategory.class))),
            @ApiResponse(responseCode = "404", description = "SubCategories not found")
    })
    public List<SubCategory> getAllSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        return subCategoryService.getAllSubCategoriesByCategoryId(categoryId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a subcategory by ID", description = "Retrieve a subcategory by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubCategory retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SubCategory.class))),
            @ApiResponse(responseCode = "404", description = "SubCategory not found")
    })
    public SubCategory getSubCategoryById(@PathVariable Long id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a subcategory", description = "Update a subcategory with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SubCategory updated successfully",
                    content = @Content(schema = @Schema(implementation = SubCategory.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "SubCategory not found")
    })
    public SubCategory updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategoryDetails) {
        return subCategoryService.updateSubCategory(id, subCategoryDetails);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subcategory", description = "Delete a subcategory by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "SubCategory deleted successfully"),
            @ApiResponse(responseCode = "404", description = "SubCategory not found")
    })
    public void deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
    }
}

