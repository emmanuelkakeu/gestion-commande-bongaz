package com.users.users.controler.api;

import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UsersDto;
import io.swagger.annotations.Api;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import static com.users.users.utils.Constants.DELIVERYPERSON_ENDPOINT;
import static com.users.users.utils.Constants.USERSENDPOINT;

@RequestMapping(USERSENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "users Management", description = "Endpoint to manage users")

public interface UsersApi {

    @PostMapping( "/create")
    UsersDto save(@RequestBody UsersDto dto);

    @PostMapping( "/update/password")
    UsersDto changerMotDePasse(@RequestBody ChangerMotDePasseUtilisateurDto dto);

    @GetMapping( "/{idUtilisateur}")
    UsersDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping("/find/{email}")
    UsersDto findByEmail(@PathVariable("email") String email);

    @GetMapping("/all")
    List<UsersDto> findAll();

    @DeleteMapping( "/delete/{idUtilisateur}")
    void delete(@PathVariable("idUtilisateur") Integer id);

}
