package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Méthode pour récupérer tous les produits créés par un utilisateur spécifique
    List<Product> findByUser(User user);
}

