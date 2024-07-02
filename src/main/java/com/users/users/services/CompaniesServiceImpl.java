package com.users.users.services;


import com.users.users.dto.CompaniesDto;
import com.users.users.dto.RoleDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.models.Companies;
import com.users.users.models.Role;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.CompaniesRepository;
import com.users.users.repository.RoleRepository;
import com.users.users.repository.UsersRepository;
import com.users.users.services.auth.JwtFilter;
import com.users.users.services.interfaces.CompaniesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CompaniesServiceImpl implements CompaniesService {


    private final CompaniesRepository companiesRepository;
    private final FileService fileService;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



    @Autowired
    public CompaniesServiceImpl(CompaniesRepository companiesRepository, FileService fileService, UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {

        this.companiesRepository = companiesRepository;
        this.fileService = fileService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public CompaniesDto save(CompaniesDto dto, MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            log.error("Image file is empty or null");
            throw new IllegalArgumentException("Veuillez sélectionner une image.");
        }

        String imageFileName = fileService.saveImage(imageFile);
        if (imageFileName == null || imageFileName.isEmpty()) {
            log.error("Image file name is empty or null after saving");
            throw new IOException("Failed to save image file.");
        }

        dto.setImageFileName(imageFileName);
        if (!dto.getEmail().contains("@") || !dto.getEmail().contains(".")){
            throw new RuntimeException("Votre mail est invalide");
        }

        if (userAlreadyExists(dto.getEmail())) {
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le même email existe déjà dans la BDD"));
        }

        String mdpCrypte = this.passwordEncoder.encode(dto.getPassword());
        dto.setPassword(mdpCrypte);

        Role role = roleRepository.findByLibelle(TypeRole.COMPANIESCLIENT)
                .orElseGet(() -> {
                    // Créer le rôle s'il n'existe pas
                    Role newRole = new Role();
                    newRole.setLibelle(TypeRole.COMPANIESCLIENT);
                    return roleRepository.save(newRole);
                });
        dto.setRole(role);

        Companies savedUser = companiesRepository.save(CompaniesDto.toEntity(dto));
        return CompaniesDto.fromEntity(savedUser);
    }

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = usersRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public CompaniesDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return companiesRepository.findById(id)
                .map(CompaniesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<CompaniesDto> findAll() {
        return companiesRepository.findAll().stream()
                .map(CompaniesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Client ID is null");
            return;
        }
//        List<CommandeCompanies> commandeCompanies = commandeCompaniesRepository.findAllByCompaniesId(id);
//        if (!commandeCompanies.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer l'entrepriseCliente qui a deja des commande ",
//                    ErrorCodes.COMPANIES_ALREADY_IN_USE);
//        }
        companiesRepository.deleteById(id);

    }
}
