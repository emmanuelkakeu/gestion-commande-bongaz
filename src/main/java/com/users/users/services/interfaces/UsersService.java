package com.users.users.services;

import com.users.users.dto.ChangerMotDePasseUtilisateurDto;
import com.users.users.dto.UsersDto;

import java.util.List;

public interface UsersrService {

    UsersDto save(UsersDto dto);

    UsersDto findById(Integer id);

    List<UsersDto> findAll();

    void delete(Integer id);

    UsersDto findByEmail(String email);

    UsersDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);

}
