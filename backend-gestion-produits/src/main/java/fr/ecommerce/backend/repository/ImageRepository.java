package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
