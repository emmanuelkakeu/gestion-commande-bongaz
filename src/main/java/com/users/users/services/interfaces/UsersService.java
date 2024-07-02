package com.users.users.services.interfaces;

import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UtilisateurDto;
import com.users.users.models.Utilisateurs;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsersService {

    UtilisateurDto inscription(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

    UtilisateurDto findByEmail(String email);

    void updateImageForUser(Integer id, MultipartFile imageFile) throws IOException;

    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);


    void completeRegistration(String username);
}
