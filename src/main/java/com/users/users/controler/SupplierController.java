package com.users.users.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.users.users.controler.api.SupplierApi;
import com.users.users.dto.SupplierDto;
import com.users.users.services.interfaces.SupplierService;
import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.SUPPLIERS_ENDPOINT;

@RestController
public class SupplierController implements SupplierApi {

    private final SupplierService supplierService;
    private final ObjectMapper objectMapper; // Ajoutez l'objet ObjectMapper

    @Autowired
    public SupplierController(SupplierService supplierService, ObjectMapper objectMapper) {
        this.supplierService = supplierService;
        this.objectMapper = objectMapper; // Injectez l'objet ObjectMapper
        // Configurez le module JavaTimeModule pour prendre en charge les types de date/heure Java 8

    }

    @Override
    @PostMapping(value = SUPPLIERS_ENDPOINT + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SupplierDto save(@RequestPart("supplierDTO") String supplierJson,
                            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        SupplierDto supplierDto = objectMapper.readValue(supplierJson, SupplierDto.class);
        return supplierService.save(supplierDto, imageFile);
    }


    @Override
    public SupplierDto findById(Integer id) {
        return supplierService.findById(id);
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierService.findAll();
    }

    @Override
    public void delete(Integer id) {
        supplierService.delete(id);
    }
}
