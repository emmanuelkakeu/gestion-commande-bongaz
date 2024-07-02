package com.users.users.controler;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.users.users.controler.api.UsersApi;
import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UtilisateurDto;
import com.users.users.models.GasRetailer;
import com.users.users.models.Utilisateurs;
import com.users.users.models.auth.AuthentificationDTO;
import com.users.users.models.auth.RequestResultDto;
import com.users.users.models.auth.VerifyOtpRequest;
import com.users.users.repository.UsersRepository;
import com.users.users.services.auth.EmailService;
import com.users.users.services.auth.JwtService;
import com.users.users.services.auth.OtpService;
import com.users.users.services.interfaces.UsersService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import static com.users.users.utils.Constants.USERSENDPOINT;

@Slf4j
@RestController
public class UtilisateurController implements UsersApi {

    private final UsersService usersService;
    private  final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final OtpService otpService;
    private final EmailService emailService;



    @Autowired
    public UtilisateurController(UsersService usersService, JwtService jwtService, AuthenticationManager authenticationManager, UsersRepository usersRepository, OtpService otpService, EmailService emailService) {
        this.usersService = usersService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.otpService = otpService;
        this.emailService = emailService;

    }

    @Override
    @PostMapping(USERSENDPOINT + "/inscription")
    public ResponseEntity<RequestResultDto<Map<String, String>>> inscription(@RequestBody UtilisateurDto dto) {
        // Crée l'utilisateur
        usersService.inscription(dto);

        // Génère et envoie l'OTP
        String otp = otpService.generateOtp();
        emailService.sendOtpEmail(dto.getEmail(), otp);
        otpService.saveOtp(dto.getEmail(), otp);

        // Retourne une réponse indiquant que l'OTP a été envoyé
        RequestResultDto<Map<String, String>> response = new RequestResultDto<>();
        response.setCode(200);
        response.setMessage("OTP sent to your email");
        response.setStatus("pending");
        response.setTimestamp(Instant.now());

        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping(USERSENDPOINT + "/verify-otp")
    public ResponseEntity<RequestResultDto<Map<String, Object>>> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean isValid = otpService.verifyOtp(request.getUsername(), request.getOtp());
        if (isValid) {
            // Complète le processus d'inscription
            usersService.completeRegistration(request.getUsername());

            // Prépare les informations d'authentification
            AuthentificationDTO authentificationDTO = new AuthentificationDTO(
                    request.getUsername(), request.getPassword()
            );

            // Authentifie l'utilisateur
            return this.connexion(authentificationDTO);
        } else {
            RequestResultDto<Map<String, Object>> response = new RequestResultDto<>();
            response.setCode(400);
            response.setMessage("Invalid OTP");
            response.setStatus("error");
            response.setTimestamp(Instant.now());

            return ResponseEntity.status(400).body(response);
        }
    }


    @Override
    @PostMapping(USERSENDPOINT + "/connexion")
    public ResponseEntity<RequestResultDto<Map<String, Object>>> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        try {
            // Authentifier l'utilisateur
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
            );

            // Vérifier si l'authentification a réussi
            if (authenticate.isAuthenticated()) {
                // Générer le JWT
                Map<String, String> jwtMap = this.jwtService.generate(authentificationDTO.username());

                // Récupérer les détails de l'utilisateur
                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
                Utilisateurs utilisateur = usersRepository.findByEmail(userDetails.getUsername())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non trouvé"));

                // Convertir en DTO
                UtilisateurDto utilisateurDto = UtilisateurDto.fromEntity(utilisateur);

                // Préparer les données de réponse
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("jwt", jwtMap.get(JwtService.BEARER)); // Assurez-vous que cela correspond aux attentes
                responseData.put("user", utilisateurDto); // Convertir en String si nécessaire

                // Créer la réponse
                RequestResultDto<Map<String, Object>> response = new RequestResultDto<>();
                response.setCode(200);
                response.setData(responseData); // Utiliser responseData ici
                response.setMessage("Request processed successfully");
                response.setStatus("success");
                response.setTimestamp(Instant.now());

                return ResponseEntity.ok(response);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials", e);
        }
    }

    @Override
    @PostMapping(value = USERSENDPOINT + "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImageForUser(@PathVariable Integer id, @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            usersService.updateImageForUser(id, imageFile);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
        return usersService.changerMotDePasse(dto);
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        return usersService.findById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return usersService.findByEmail(email);
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return usersService.findAll();
    }

    @Override
    public void delete(Integer id) {
        usersService.delete(id);
    }



}
