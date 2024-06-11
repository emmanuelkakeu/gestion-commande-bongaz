package com.users.users.controler.api;

import com.users.users.dto.IndividualClientDto;

import java.io.IOException;
import java.util.List;

import com.users.users.dto.SupplierDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.users.users.utils.Constants.*;

@RequestMapping(INDIVIDUALCLIENT_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "IndividualClient Management", description = "Endpoint to manage IndividualClient")
public interface IndividualClientApi {

    @PostMapping(value =  "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto save(@RequestPart("individualClientDto") String individualClientJson,
                             @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value =  "/individualClient/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value =  "/individualClient/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<IndividualClientDto> findAll();

    @DeleteMapping(value =  "/individualClient/delete/{idIndividualClient}")
    void delete(@PathVariable("idIndividualClient") Integer id);

}
