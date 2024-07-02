package com.users.users.services;

import com.users.users.dto.GasRetailerDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.models.GasRetailer;
import com.users.users.models.Role;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.RetailerRepository;
import com.users.users.repository.RoleRepository;
import com.users.users.repository.UsersRepository;
import com.users.users.services.interfaces.GasRetailerService;
import com.users.users.validators.GasRetailerValidator;
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
public class GasRetailerServiceImpl implements GasRetailerService {


    private final RetailerRepository retailerRepository;
    private final FileService fileService;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;





    @Autowired
    public GasRetailerServiceImpl(RetailerRepository retailerRepository, FileService fileService, UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.retailerRepository = retailerRepository;
        this.fileService = fileService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public GasRetailerDto save(GasRetailerDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = GasRetailerValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("GasRetailer is not valid {}", dto);
            throw new InvalidEntityException("Le GasRetailer n'est pas valide", ErrorCodes.COMPANIES_NOT_VALID, errors);
        }

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);

        if (userAlreadyExists(dto.getEmail())) {
            throw new InvalidEntityException("Un autre utilisateur avec le même email existe déjà",
                    ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
                    Collections.singletonList("Un autre utilisateur avec le même email existe déjà dans la BDD"));
        }

        String mdpCrypte = this.passwordEncoder.encode(dto.getPassword());
        dto.setPassword(mdpCrypte);

        Role role = roleRepository.findByLibelle(TypeRole.GASRETAILER)
                .orElseGet(() -> {
                    // Créer le rôle s'il n'existe pas
                    Role newRole = new Role();
                    newRole.setLibelle(TypeRole.GASRETAILER);
                    return roleRepository.save(newRole);
                });
        dto.setRole(role);

        return GasRetailerDto.fromEntity(
                retailerRepository.save(
                        GasRetailerDto.toEntity(dto)
                )
        );
    }

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = usersRepository.findByEmail(email);
        return user.isPresent();
    }


    @Override
    public GasRetailerDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return retailerRepository.findById(id)
                .map(GasRetailerDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<GasRetailerDto> findAll() {
        return retailerRepository.findAll().stream()
                .map(GasRetailerDto::fromEntity)
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
        retailerRepository.deleteById(id);

    }


    @Override
    public List<GasRetailerDto> getNearbyRetailers(double lat, double lng) {
        List<GasRetailer> nearbyRetailers = retailerRepository.findNearbyRetailers(lat, lng);
        return nearbyRetailers.stream()
                .map(GasRetailerDto::fromEntity)
                .collect(Collectors.toList());
    }
}
