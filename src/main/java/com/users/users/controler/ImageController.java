package com.users.users.controler;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/images")
public class ImageController {
    @GetMapping("/vue/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws MalformedURLException {
        if (imageName == null || imageName.isEmpty() || imageName.equals("undefined")) {
            return ResponseEntity.badRequest().body(null); // renvoyer une réponse HTTP 400 Bad Request
        }

        String imageDirectory = "src/main/resources/static/images/";
        Path imagePath = Paths.get(imageDirectory, imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file or file does not exist!");
        }
    }
}