package com.users.users.services;


import com.users.users.dto.SupplierDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.repository.SupplierRepository;
import com.users.users.services.interfaces.SupplierService;
import com.users.users.validators.SupplierValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final FileService fileService;
   @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, FileService fileService) {
        this.supplierRepository = supplierRepository;
        this.fileService = fileService;
    }


    @Override
    public SupplierDto save(SupplierDto dto, MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            ResponseEntity.badRequest().body("Veuillez sélectionner une image.");
        }
        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);
        List<String> errors = SupplierValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Fournisseur is not valid {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }

        return SupplierDto.fromEntity(
                supplierRepository.save(
                        SupplierDto.toEntity(dto)
                )
        );
    }

    @Override
    public SupplierDto findById(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return null;
        }
        return supplierRepository.findById(id)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll().stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return;
        }

        supplierRepository.deleteById(id);
    }

}