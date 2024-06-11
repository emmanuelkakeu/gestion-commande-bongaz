package com.users.users.controler;

import com.users.users.models.Supplier;
import com.users.users.services.interfaces.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Supplier Management", description = "Endpoint to manage suppliers")
@RestController
@RequestMapping("/api/suppliers")
public class SupplierControllerre {

//    @Autowired
//    private SupplierService supplierService;
//
//    @Operation(summary = "Add a new supplier")
//    @PostMapping(consumes = "multipart/form-data")
////    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Supplier> addSupplier(
//            @RequestPart("supplierDTO") SupplierDTO supplierDTO,
//            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
//
//        Supplier createdSupplier = supplierService.addSupplier(supplierDTO, imageFile);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
//    }
//
//    @Operation(summary = "Update an existing supplier")
//    @PutMapping("/{id}")
//    public ResponseEntity<SupplierDTO> updateSupplier(
//            @Parameter(description = "Supplier ID") @PathVariable Long id,
//            @Parameter(description = "Supplier details") @RequestBody SupplierDTO supplierDTO,
//            @Parameter(description = "Image file") @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
//        SupplierDTO updatedSupplier = supplierService.updateSupplier(id, supplierDTO, imageFile);
//        return ResponseEntity.ok(updatedSupplier);
//    }
//
//    @Operation(summary = "Get a supplier by ID")
//    @GetMapping("/{id}")
//    public ResponseEntity<SupplierDTO> getSupplierById(
//            @Parameter(description = "Supplier ID") @PathVariable Long id) {
//        SupplierDTO supplierDTO = supplierService.getSupplierById(id);
//        return ResponseEntity.ok(supplierDTO);
//    }
//
//    @Operation(summary = "Get all suppliers")
//    @GetMapping
//    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
//        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
//        return ResponseEntity.ok(suppliers);
//    }
//
//    @Operation(summary = "Delete a supplier")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSupplier(
//            @Parameter(description = "Supplier ID") @PathVariable Long id) {
//        supplierService.deleteSupplier(id);
//        return ResponseEntity.noContent().build();
//    }
}
