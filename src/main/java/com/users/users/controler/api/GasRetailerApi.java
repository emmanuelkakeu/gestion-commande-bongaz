package com.users.users.controler.api;

import com.users.users.dto.GasRetailerDto;
import com.users.users.models.GasRetailer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.*;


@RequestMapping("/gestionUtilisateurs/v1")
@Tag(name = "GasRetailer Management", description = "Endpoint to manage GasRetailer")

public interface GasRetailerApi {

    @PostMapping( value = GASRETAILER_ENDPOINT +"/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    GasRetailerDto save(@RequestPart("gasRetailerDTO") String GasRetailerJson,
                        @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value = GASRETAILER_ENDPOINT +"/{idGasRetailer}", produces = MediaType.APPLICATION_JSON_VALUE)
    GasRetailerDto findById(@PathVariable("idGasRetailer") Integer id);

    @GetMapping(value =GASRETAILER_ENDPOINT +"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<GasRetailerDto> findAll();

    @DeleteMapping(value =GASRETAILER_ENDPOINT + "/delete/{idGasRetailer}")
    void delete(@PathVariable("idGasRetailer") Integer id);

    @GetMapping(value = GASRETAILER_ENDPOINT +"/nearby")
    List<GasRetailerDto> getNearbyRetailers(@RequestParam("lat") double latitude, @RequestParam("lng") double longitude);

}
