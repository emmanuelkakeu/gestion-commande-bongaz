package com.users.users.services;

import com.users.users.dto.DeliveryPersonDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.models.DeliveryPerson;
import com.users.users.models.Role;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.DeliveryManRepository;
import com.users.users.repository.RoleRepository;
import com.users.users.repository.UsersRepository;
import com.users.users.services.interfaces.DeliveryManService;
import com.users.users.validators.DeliveryManValidator;
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
public class DeliveryManServiceImpl implements DeliveryManService {

    private final DeliveryManRepository deliveryManRepository;
    private final FileService fileService;
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public DeliveryManServiceImpl(DeliveryManRepository deliveryManRepository, FileService fileService, UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.deliveryManRepository = deliveryManRepository;
        this.fileService = fileService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public DeliveryPersonDto save(DeliveryPersonDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = DeliveryManValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
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

        Role role = roleRepository.findByLibelle(TypeRole.DELIVERYMAN)
                .orElseGet(() -> {
                    // Créer le rôle s'il n'existe pas
                    Role newRole = new Role();
                    newRole.setLibelle(TypeRole.DELIVERYMAN);
                    return roleRepository.save(newRole);
                });
        dto.setRole(role);


        return DeliveryPersonDto.fromEntity(
                deliveryManRepository.save(
                        DeliveryPersonDto.toEntity(dto)
                )
        );
    }

    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = usersRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public List<DeliveryPersonDto> getAllDeliveryPersonsByVille(String ville) {
        List<DeliveryPerson> deliveryPersons = deliveryManRepository.findByAdresseVille(ville);
        return deliveryPersons.stream()
                .map(DeliveryPersonDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryPersonDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return deliveryManRepository.findById(id)
                .map(DeliveryPersonDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<DeliveryPersonDto> findAll() {
        return deliveryManRepository.findAll().stream()
                .map(DeliveryPersonDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        deliveryManRepository.deleteById(id);
    }


}
