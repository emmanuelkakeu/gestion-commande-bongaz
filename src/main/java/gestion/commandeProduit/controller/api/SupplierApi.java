package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.SupplierDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.SUPPLIERS_ENDPOINT;

//@Api("supplier")
@RequestMapping(SUPPLIERS_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "supplier Management", description = "Endpoint to manage supplier")


public interface SupplierApi {

    @PostMapping( "/create")
    SupplierDto save(@RequestBody SupplierDto dto);

    @GetMapping("/{idFournisseur}")
    SupplierDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping( "/all")
    List<SupplierDto> findAll();

    @DeleteMapping( "/delete/{idFournisseur}")
    void delete(@PathVariable("idFournisseur") Integer id);

}
