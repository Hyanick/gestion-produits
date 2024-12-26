package fr.ecommerce.backend.controller;

import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestParam String username, @RequestBody Product product) {
        return productService.createProduct(username, product);
    }

    @GetMapping("/user/{username}")
    public List<Product> getProductsByUsernameOrderByCreatedAt(@PathVariable String username) {
        return productService.getProductsByUsernameOrderByCreatedAt(username);
    }
  /*  public List<Product> getProductsByUsername(@PathVariable String username) {
        return productService.getProductsByUsername(username);
    }*/





/*
    // Endpoint pour créer un produit
    @PostMapping
    public Product createProduct(@RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price) {
        return productService.createProduct(name, description, price);
    }

    // Endpoint pour obtenir la liste des produits créés par l'utilisateur authentifié
    @GetMapping
    public List<Product> getUserProducts() {
        // Logique pour retourner les produits de l'utilisateur connecté
        return productService.getUserProducts();
    }
    */
}
