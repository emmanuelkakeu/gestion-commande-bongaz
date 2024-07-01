package gestion.commandeProduit.controller;

import gestion.commandeProduit.controller.api.ImageControlerApi;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;


@RestController
public class ImageControllerImpl implements ImageControlerApi {

    @Override
    public ResponseEntity<Resource> getImage(String imageName) throws IOException {
        if (imageName == null || imageName.isEmpty() || imageName.equals("undefined")) {
            return ResponseEntity.badRequest().body(null); // HTTP 400 Bad Request
        }

        String imageDirectory = "src/main/resources/static/images/";
        Path imagePath = Paths.get(imageDirectory, imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            String contentType = Files.probeContentType(imagePath);
            MediaType mediaType = MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream");
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } else {
            // Enregistrer les informations détaillées sur l'erreur
            String errorMessage = "Impossible de lire le fichier ou le fichier n'existe pas!";
            System.err.println(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // HTTP 500 Internal Server Error
        }
    }

    private String getContentType(String imageName) {
        if (imageName.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
