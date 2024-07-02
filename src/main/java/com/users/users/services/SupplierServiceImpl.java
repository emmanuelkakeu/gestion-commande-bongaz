package com.users.users.services;


import com.users.users.dto.RoleDto;
import com.users.users.dto.SupplierDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.models.Role;
import com.users.users.models.Supplier;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.RoleRepository;
import com.users.users.repository.SupplierRepository;
import com.users.users.repository.UsersRepository;
import com.users.users.services.auth.JwtFilter;
import com.users.users.services.interfaces.SupplierService;
import com.users.users.validators.SupplierValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final FileService fileService;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


   @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, FileService fileService, UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.supplierRepository = supplierRepository;
        this.fileService = fileService;
       this.usersRepository = usersRepository;
       this.passwordEncoder = passwordEncoder;
       this.roleRepository = roleRepository;
   }


    @Override
    public SupplierDto save(SupplierDto dto, MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            ResponseEntity.badRequest().body("Veuillez sélectionner une image.");
        }
        List<String> errors = SupplierValidator.validate(dto);

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);

        if (userAlreadyExists(dto.getEmail())) {
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le même email existe déjà dans la BDD"));
        }

        String mdpCrypte = this.passwordEncoder.encode(dto.getPassword());
        dto.setPassword(mdpCrypte);


        Role role = roleRepository.findByLibelle(TypeRole.SUPPLIER)
                .orElseGet(() -> {
                    // Créer le rôle s'il n'existe pas
                    Role newRole = new Role();
                    newRole.setLibelle(TypeRole.SUPPLIER);
                    return roleRepository.save(newRole);
                });
        dto.setRole(role);

        // Validation et enregistrement
        return SupplierDto.fromEntity(
                supplierRepository.save(
                        SupplierDto.toEntity(dto)
                )
        );
    }

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = usersRepository.findByEmail(email);
        return user.isPresent();
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