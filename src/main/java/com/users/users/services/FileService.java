package com.users.users.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;

@Service
public class FileService {

    private String generateRandomName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public String saveImage(MultipartFile imageFile) throws IOException {
        String imageName = this.generateRandomName() + "." + (Objects.requireNonNull(imageFile.getContentType()).split("/"))[1];
        String imageDirectory = "src/main/resources/static/images/";
        Path path = Paths.get(imageDirectory);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path filePath = path.resolve(imageName);

        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException exception) {
            throw new IOException("Error while uploading image: " + exception.getMessage(), exception);
        }
        return imageName;
    }
}
