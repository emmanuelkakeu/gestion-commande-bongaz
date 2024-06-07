package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CompaniesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.COMPANIES_ENDPOINT;

//@Api("companies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = " Companies Management", description = "Endpoint to manage commande ")


@RequestMapping(COMPANIES_ENDPOINT)
public interface CompaniesApi {

    @PostMapping(value =  "/companies/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto save(@RequestBody CompaniesDto dto);

    @GetMapping(value =  "/companies/{idcompanies}", produces = MediaType.APPLICATION_JSON_VALUE)
    CompaniesDto findById(@PathVariable("idcompanies") Integer id);

    @GetMapping(value =  "/companies/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<CompaniesDto> findAll();

    @DeleteMapping(value =  "/companies/delete/{idcompanies}")
    void delete(@PathVariable("idcompanies") Integer id);

}

