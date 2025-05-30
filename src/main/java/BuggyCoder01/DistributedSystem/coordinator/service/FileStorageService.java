package BuggyCoder01.DistributedSystem.coordinator.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.nio.file.*;

@Service
public class FileStorageService {

    @Value("${file.storage.location}")
    private String storageLocation;

    private Path root;

    @PostConstruct
    public void init() {
        this.root = Paths.get(storageLocation);
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}