package fr.ecommerce.backend.repository;

import fr.ecommerce.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
   //Optional<User> findByEmail(String email);
   User findByUsername(String username);

    @Query("from User u where u.name like %?1% or u.email like %?1%")
    List<User> findByNameOrEmailLike(String query);
}