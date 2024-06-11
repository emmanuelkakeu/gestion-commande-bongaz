package com.users.users.controler.api;

import com.users.users.dto.SupplierDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.SUPPLIERS_ENDPOINT;

@RequestMapping(SUPPLIERS_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "supplier Management", description = "Endpoint to manage supplier")
public interface SupplierApi {

    @Operation(summary = "Create a new supplier")

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SupplierDto save(@RequestPart("supplierDTO") String supplierJson,
                     @RequestPart("imageFile") MultipartFile imageFile) throws IOException;

    @GetMapping("/{idFournisseur}")
    SupplierDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping("/all")
    List<SupplierDto> findAll();

    @DeleteMapping("/delete/{idFournisseur}")
    void delete(@PathVariable("idFournisseur") Integer id);
}
