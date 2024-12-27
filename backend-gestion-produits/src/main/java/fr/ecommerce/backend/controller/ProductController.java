package fr.ecommerce.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ecommerce.backend.model.Product;
import fr.ecommerce.backend.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // Pour tester les ajouts de  produits avec images dans postman
    @PostMapping
    public Product createProduct(@RequestParam String username, @RequestParam Long categoryId, @RequestParam(required = false) Long subCategoryId, @RequestParam String productJson, @RequestParam("images") MultipartFile[] images) throws IOException {
        Product product = objectMapper.readValue(productJson, Product.class);
        return productService.createProduct(username, categoryId, subCategoryId, product, Set.of(images));
    }

 /*   @PostMapping
    public Product createProduct(@RequestParam String username, @RequestPart Product product, @RequestPart Set<MultipartFile> images) throws IOException {
        return productService.createProduct(username, product, images);
    }
*/
  /*  @PostMapping
    public Product createProduct(@RequestParam String username, @RequestBody Product product) {
        return productService.createProduct(username, product);
    }
*/
    @GetMapping("/user/{username}")
    public List<Product> getProductsByUsernameOrderByCreatedAt(@PathVariable String username) {
        return productService.getProductsByUsernameOrderByCreatedAt(username);
    }

    @GetMapping
    public List<Product> getAllProductsOrderByCreatedAt() {
        return productService.getAllProductsOrderByCreatedAt();
    }

    @GetMapping("/search")
    public List<Product> searchProductsByKeyword(@RequestParam String keyword) {
        return productService.searchProductsByKeyword(keyword);
    }

    @GetMapping("/search/category")
    public List<Product> searchProductsByCategoryName(@RequestParam String categoryName) {
        return productService.searchProductsByCategoryName(categoryName);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
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
