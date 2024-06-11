package com.users.users.controler.api;

import com.users.users.dto.CompaniesDto;
import com.users.users.dto.IndividualClientDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.*;

@RequestMapping(COMPANIES_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Companies Management", description = "Endpoint to manage Companies")
public interface CompaniesApi {

    @PostMapping( value ="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto save(@RequestPart("companiesDto") String individualClientJson,
                      @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value = "/Companies/{idCompanies}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto findById(@PathVariable("idCompanies") Integer id);

    @GetMapping(value ="/Companies/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompaniesDto> findAll();

    @DeleteMapping(value = "/Companies/delete/{idCompanies}")
    void delete(@PathVariable("idCompanies") Integer id);

}
