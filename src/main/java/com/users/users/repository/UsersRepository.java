package com.users.users.repository;


import com.users.users.models.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Utilisateurs, Integer> {
    Optional<Utilisateurs> findByEmail(String email);

//    Optional<Utilisateurs> findByEmail(String email);

    Optional<Utilisateurs> findUtilisateurByEmail(String email);
}
