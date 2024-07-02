package com.users.users.controler.api;

import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UtilisateurDto;
import com.users.users.models.GasRetailer;
import com.users.users.models.auth.AuthentificationDTO;
import com.users.users.models.auth.RequestResultDto;
import com.users.users.models.auth.VerifyOtpRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.users.users.utils.Constants.USERSENDPOINT;


@RequestMapping("/gestionUtilisateurs/v1")
@Tag(name = "users Management", description = "Endpoint to manage users")
//@Api("UsersApi")
public interface UsersApi {

    @PostMapping(USERSENDPOINT + "/inscription")
    ResponseEntity<RequestResultDto<Map<String, String>>> inscription(@RequestBody UtilisateurDto dto);

    @PostMapping("/verify-otp")
    ResponseEntity<RequestResultDto<Map<String, Object>>> verifyOtp(@RequestBody VerifyOtpRequest request);

    @PostMapping(USERSENDPOINT + "/connexion")
    ResponseEntity<RequestResultDto<Map<String, Object>>> connexion(@RequestBody AuthentificationDTO authentificationDTO);


    @PostMapping(value = USERSENDPOINT + "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> updateImageForUser(@PathVariable Integer id, @RequestPart("imageFile") MultipartFile imageFile);

    @PostMapping(USERSENDPOINT + "/update/password")
    UtilisateurDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    @GetMapping( USERSENDPOINT +"/{idUtilisateur}")
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(USERSENDPOINT +"/find/{email}")
    UtilisateurDto findByEmail(@PathVariable("email") String email);

    @GetMapping(USERSENDPOINT +"/all")
    List<UtilisateurDto> findAll();

    @DeleteMapping( USERSENDPOINT +
            "/delete/{idUtilisateur}")
    void delete(@PathVariable("idUtilisateur") Integer id);


}
