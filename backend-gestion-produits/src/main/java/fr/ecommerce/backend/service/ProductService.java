package fr.ecommerce.backend.service;

import fr.ecommerce.backend.exceptions.ResourceNotFoundException;
import fr.ecommerce.backend.model.*;
import fr.ecommerce.backend.repository.CategoryRepository;
import fr.ecommerce.backend.repository.ImageRepository;
import fr.ecommerce.backend.repository.ProductRepository;
import fr.ecommerce.backend.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageService imageService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;



    public Product createProduct(String username, Long categoryId, Long subCategoryId, Product product, Set<MultipartFile> images) throws IOException {
        User user = userService.findByUsername(username);
        Category category = categoryService.getCategoryById(categoryId);
        SubCategory subCategory = null;

        if (subCategoryId != null) {
            subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        }

        product.setUser(user);
        product.setCategory(category);
        product.setSubCategory(subCategory);

        Product savedProduct = productRepository.save(product);

        if (images != null) {
            for (MultipartFile imageFile : images) {
                String publicUrl = imageService.saveImage(imageFile);
                String imageUrl = imageService.getImageUrl(publicUrl);
                Image image = new Image();
                image.setUrl(imageUrl);
                // Enregistrer les métadonnées dans la base de données

                image.setFileName(imageFile.getOriginalFilename());
               // image.setFilePath(filePath.toString());
               image.setUrl(publicUrl);
                image.setContentType(imageFile.getContentType());
                image.setSize(imageFile.getSize());
                image.setUploadDate(LocalDateTime.now());
                image.setProduct(savedProduct);
                imageRepository.save(image);
            }
        }

        return savedProduct;
    }

    /*

    // Pour tester les ajouts de  produits avec images dans postman
    public Product createProduct(String username, Product product, Set<MultipartFile> images) throws IOException {
        User user = userService.findByUsername(username);
        product.setUser(user);

        Product savedProduct = productRepository.save(product);

        if (images != null) {
            for (MultipartFile imageFile : images) {
                String uniqueFileName = imageService.saveImage(imageFile);
                String imageUrl = imageService.getImageUrl(uniqueFileName);
                Image image = new Image();
                image.setUrl(imageUrl);
                image.setProduct(savedProduct);
                imageRepository.save(image);
            }


        }

        return savedProduct;
    }
*/
    /*
    public Product createProduct(String username, Product product, Set<MultipartFile> images) throws IOException {
        // Assuming you have a method to find the user by username
        User user = userService.findByUsername(username);
        product.setUser(user);

        // Save the product first to get the generated ID
        Product savedProduct = productRepository.save(product);

        // Save the images and associate them with the product
        if (images != null) {
            for (MultipartFile imageFile : images) {
                String fileName = imageService.saveImage(imageFile);
                String imageUrl = imageService.getImageUrl(fileName);
                Image image = new Image();
                image.setUrl(imageUrl);
                image.setProduct(savedProduct);
                imageRepository.save(image);
            }
        }

        return savedProduct;
    }
*/
    /*
    public Product createProduct(String username, Product product) {
        User user = userService.findByUsername(username);
        product.setUser(user);
        return productRepository.save(product);
    }
*/
    public List<Product> getProductsByUsername(String username) {
        return productRepository.findByUserUsername(username);
    }

    public List<Product> getProductsByUsernameOrderByCreatedAt(String username) {
        return productRepository.findByUserUsernameOrderByCreatedAtDesc(username);
    }

    public List<Product> getAllProductsOrderByCreatedAt() {
        return productRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        // Delete associated images
        for (Image image : product.getImages()) {
            imageRepository.delete(image);
            imageService.deleteImage(image.getUrl());
        }

        // Delete the product
        productRepository.delete(product);
    }

    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByKeyword(keyword).stream()
                .peek(product -> Hibernate.initialize(product.getImages()))
                .collect(Collectors.toList());
    }

    public List<Product> searchProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream()
                .peek(product -> Hibernate.initialize(product.getImages()))
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
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


