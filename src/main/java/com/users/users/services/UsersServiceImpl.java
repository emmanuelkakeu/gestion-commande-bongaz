package com.users.users.services;

import com.users.users.dto.AdresseDto;
import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UtilisateurDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.exception.InvalidOperationException;
import com.users.users.models.IndividualClient;
import com.users.users.models.Companies;
import com.users.users.models.Role;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.Status;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.RoleRepository;
import com.users.users.repository.UsersRepository;
import com.users.users.services.auth.JwtFilter;
import com.users.users.services.interfaces.UsersService;
import com.users.users.validators.UsersValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UsersServiceImpl implements UsersService, UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FileService fileService;


    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, FileService fileService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.fileService = fileService;
    }

    public UtilisateurDto inscription(UtilisateurDto dto) {
        List<String> errors = UsersValidator.validate(dto);
//        if (!errors.isEmpty()) {
//            log.error("Utilisateur is not valid {}", dto);
//            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
//        }

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

        Utilisateurs utilisateurEntity = switch (dto.getTypeUtilisateur().toLowerCase()) {
            case "individualclient" -> new IndividualClient();
            case "companies" -> new Companies();
            default ->
                    throw new InvalidEntityException("Type d'utilisateur non reconnu", ErrorCodes.UTILISATEUR_NOT_VALID, Collections.singletonList("Type d'utilisateur non reconnu"));
        };

        utilisateurEntity.setFirstName(dto.getFirstName());
        utilisateurEntity.setName(dto.getName());
        utilisateurEntity.setLastName(dto.getLastName());
        utilisateurEntity.setEmail(dto.getEmail());
        utilisateurEntity.setPassword(dto.getPassword());
        utilisateurEntity.setContactDetails(dto.getContactDetails());
        utilisateurEntity.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        utilisateurEntity.setStatus(Status.ACTIVER);
        utilisateurEntity.setDateCreated(new Date());
        utilisateurEntity.setImageFileName(dto.getImageFileName());

        Role role = switch (dto.getTypeUtilisateur().toLowerCase()) {
            case "individualclient" -> roleRepository.findByLibelle(TypeRole.INDIVIDUALCLIENT)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setLibelle(TypeRole.INDIVIDUALCLIENT);
                        return roleRepository.save(newRole);
                    });
            case "companies" -> roleRepository.findByLibelle(TypeRole.COMPANIESCLIENT)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setLibelle(TypeRole.COMPANIESCLIENT);
                        return roleRepository.save(newRole);
                    });

            default -> throw new InvalidEntityException("Type d'utilisateur non reconnu", ErrorCodes.UTILISATEUR_NOT_VALID, Collections.singletonList("Type d'utilisateur non reconnu"));
        };

        utilisateurEntity.setRole(role);

        log.info("Utilisateur inscrit");

        return UtilisateurDto.fromEntity(usersRepository.save(utilisateurEntity));
    }


    private boolean userAlreadyExists(String email) {
        Optional<Utilisateurs> user = usersRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null) {
            log.error("Utilisateur ID is null");
            return null;
        }
        return usersRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return usersRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Utilisateur ID is null");
            return;
        }
        usersRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return usersRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'email = " + email + " n' ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public void updateImageForUser(Integer id, MultipartFile imageFile) throws IOException {

        String imageFileName = fileService.saveImage(imageFile);
        Optional<Utilisateurs> optionalUtilisateur = usersRepository.findById(id);

        if (optionalUtilisateur.isPresent()) {
            Utilisateurs utilisateur = optionalUtilisateur.get();

            utilisateur.setImageFileName(imageFileName);
            usersRepository.save(utilisateur);
        } else {
            throw new EntityNotFoundException("Utilisateur avec l'ID = " + id + " n'a pas été trouvé dans la base de données",
                    ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
    }


    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        validate(dto);
        Optional<Utilisateurs> utilisateurOptional = usersRepository.findById(dto.getId());
        if (utilisateurOptional.isEmpty()) {
            log.warn("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateurs users = utilisateurOptional.get();
//        users.setPassword(passwordEncoder.encode(dto.getMotDePasse()));

        return UtilisateurDto.fromEntity(
                usersRepository.save(users)
        );
    }

    @Override
    public Utilisateurs loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("aucun utilisateur ne correspond a cet identifient."));
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto) {
        if (dto == null) {
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException("Aucune information n'a ete fourni pour pouvoir changer le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (dto.getId() == null) {
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException("Mot de passe utilisateur null:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
            throw new InvalidOperationException("Mots de passe utilisateur non conformes:: Impossible de modifier le mote de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }

    @Override
    public void completeRegistration(String username) {
        Utilisateurs user = usersRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        user.setStatus(Status.ACTIVER); // Exemple de mise à jour du statut
        usersRepository.save(user);
    }


}
