package com.users.users.services;

import com.users.users.dto.SupplierDTO;
import com.users.users.models.Supplier;
import com.users.users.repository.SupplierRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Supplier addSupplier(SupplierDTO supplierDTO, MultipartFile imageFile) throws IOException {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        String url = "http://localhost:8082/api/images/vue/";
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName =  fileService.saveImage(imageFile);
            supplier.setImageFileName(imageName);
            // Update the DTO with the image name
        }
        supplier.setDateCreated(new Date());
        supplierRepository.save(supplier);
        return supplier;
    }

    @Override
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO, MultipartFile imageFile) throws IOException {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
        BeanUtils.copyProperties(supplierDTO, supplier);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageName = fileService.saveImage(imageFile);
            supplier.setImageFileName(imageName);
        }
        supplierRepository.save(supplier);
        return supplierDTO;
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
        SupplierDTO supplierDTO = new SupplierDTO();
        BeanUtils.copyProperties(supplier, supplierDTO);
        return supplierDTO;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        return suppliers.stream().map(supplier -> {
            SupplierDTO supplierDTO = new SupplierDTO();
            BeanUtils.copyProperties(supplier, supplierDTO);
            return supplierDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
