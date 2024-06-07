package gestion.commandeProduit.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "image Management", description = "Endpoint to manage image")


public interface ImageControlerApi {

        @GetMapping(value =  APP_ROOT +"/image/vue/{imageName}", produces = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Resource> getImage(@PathVariable String imageName) throws MalformedURLException;

}
