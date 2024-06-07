package com.users.users.services;

import com.users.users.dto.SupplierDTO;
import com.users.users.models.Supplier;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SupplierService {
    Supplier addSupplier(SupplierDTO supplierDTO, MultipartFile imageFile) throws IOException;
    SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO, MultipartFile imageFile) throws IOException;
    SupplierDTO getSupplierById(Long id);
    List<SupplierDTO> getAllSuppliers();
    void deleteSupplier(Long id);
}

