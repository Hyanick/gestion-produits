package fr.ecommerce.backend.service;

import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.model.User;
import fr.ecommerce.backend.repository.ProductRepository;
import fr.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Product createProduct(String username, Product product) {
        User user = userService.findByUsername(username);
        product.setUser(user);
        return productRepository.save(product);
    }

    public List<Product> getProductsByUsername(String username) {
        return productRepository.findByUserUsername(username);
    }

    public List<Product> getProductsByUsernameOrderByCreatedAt(String username) {
        return productRepository.findByUserUsernameOrderByCreatedAtDesc(username);
    }

    public List<Product> getAllProductsOrderByCreatedAt() {
        return productRepository.findAllByOrderByCreatedAtDesc();
    }




/*
    // Méthode pour créer un produit
    public Product createProduct(String name, String description, BigDecimal price) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        // Récupérer l'utilisateur par son email
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();  // Récupérer l'utilisateur

            // Créer un nouveau produit
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setUser(user);  // Associer l'utilisateur au produit

            // Sauvegarder le produit en base de données
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }
*/
/*
    // Méthode pour créer un produit
    public Product createProduct(String name, String description, BigDecimal price) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepository.findByEmail(username);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setUser(user);

        return productRepository.save(product);
    }
    */
    // Méthode pour récupérer les produits de l'utilisateur
  /*  public List<Product> getUserProducts() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional <User> user = userRepository.findByEmail(username);

        //return productRepository.findByUser(user);
    }
    */
   /*
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return productRepository.findByUser(user);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }*/
}


