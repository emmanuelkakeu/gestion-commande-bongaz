package com.users.users.controler.api;

import com.users.users.dto.CompaniesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.APP_ROOT;
import static com.users.users.utils.Constants.COMPANIES_ENDPOINT;


@RequestMapping("/gestionUtilisateurs/v1")
@Tag(name = "Companies Management", description = "Endpoint to manage Companies")

public interface CompaniesApi {

    @PostMapping( value =COMPANIES_ENDPOINT +"/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto save(@RequestPart("companiesDto") String companiesJson,
                      @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value =COMPANIES_ENDPOINT + "/{idCompanies}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto findById(@PathVariable("idCompanies") Integer id);

    @GetMapping(value =COMPANIES_ENDPOINT +"/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompaniesDto> findAll(Authentication authentication);

    @DeleteMapping(value = COMPANIES_ENDPOINT +"/delete/{idCompanies}")
    void delete(@PathVariable("idCompanies") Integer id);

}
