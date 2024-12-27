package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Category;
import fr.ecommerce.backend.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query("SELECT s FROM SubCategory s WHERE s.category = :category")
    List<SubCategory> findByCategory(@Param("category") Category category);
}

