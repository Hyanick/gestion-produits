package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Méthode pour récupérer tous les produits créés par un utilisateur spécifique
    List<Product> findByUserUsername(String username);

    // Méthode pour récupérer les produits par date de création
    @Query("SELECT p FROM Product p WHERE p.user.username = :username ORDER BY p.createdAt DESC")
    List<Product> findByUserUsernameOrderByCreatedAtDesc(@Param("username") String username);

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("from User u where u.name like %?1% or u.email like %?1%")
    List<Product> findByUser(User user);
}

