package fr.ecommerce.backend.service;

import fr.ecommerce.backend.model.Image;
import fr.ecommerce.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.server-url}")
    private String serverUrl; // URL de base du serveur HTTP (ex: http://192.168.1.23:3001/)
/*
    public String saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
*/
    /*
    public String saveImage(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;
        Path filePath = Paths.get(uploadDir, uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
*/

    public String saveImage(MultipartFile file) throws IOException {
        // Obtenir le nom du fichier original
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("Invalid file: missing original filename.");
        }

        // Obtenir l'extension du fichier
        String fileExtension = getFileExtension(originalFileName);

        // Générer un nom de fichier unique
        String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

        // Construire le chemin complet pour enregistrer le fichier localement
        Path filePath = Paths.get(uploadDir, uniqueFileName);

        // Sauvegarder le fichier sur le disque
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Construire l'URL publique pour accéder au fichier
        String publicUrl = serverUrl + uniqueFileName;

        // Enregistrer les métadonnées dans la base de données
        Image metadata = new Image();
        metadata.setFileName(originalFileName);
        metadata.setFilePath(filePath.toString());
        metadata.setUrl(publicUrl);
        metadata.setContentType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setUploadDate(LocalDateTime.now());


        return  publicUrl; // Retourner l'URL publique
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException("File must have an extension.");
        }
        return fileName.substring(lastDotIndex + 1);
    }


    public String getImageUrl(String fileName) {
        return fileName;
    }

    public void deleteImage(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    */
}

