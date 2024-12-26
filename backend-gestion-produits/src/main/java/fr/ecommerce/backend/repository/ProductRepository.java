package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Méthode pour récupérer tous les produits créés par un utilisateur spécifique
    @Query("from User u where u.name like %?1% or u.email like %?1%")
    List<Product> findByUser(User user);
}

