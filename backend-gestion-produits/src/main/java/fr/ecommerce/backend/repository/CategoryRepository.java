package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
