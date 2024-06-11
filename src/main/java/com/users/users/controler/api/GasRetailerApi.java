package com.users.users.controler.api;

import com.users.users.dto.GasRetailerDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.COMPANIES_ENDPOINT;
import static com.users.users.utils.Constants.GASRETAILER_ENDPOINT;

@RequestMapping(GASRETAILER_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "GasRetailer Management", description = "Endpoint to manage GasRetailer")
public interface GasRetailerApi {

    @PostMapping( value ="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GasRetailerDto save(@RequestPart("GasRetailerDto") String GasRetailerJson,
                        @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value = "/gasRetailer/{idGasRetailer}", produces = MediaType.APPLICATION_JSON_VALUE)
    GasRetailerDto findById(@PathVariable("idGasRetailer") Integer id);

    @GetMapping(value ="/gasRetailer/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<GasRetailerDto> findAll();

    @DeleteMapping(value = "/gasRetailer/delete/{idGasRetailer}")
    void delete(@PathVariable("idGasRetailer") Integer id);

}
