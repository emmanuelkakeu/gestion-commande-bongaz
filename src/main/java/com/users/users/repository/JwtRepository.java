package com.users.users.repository;

import com.users.users.models.auth.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {


    @Query("SELECT j FROM Jwt j LEFT JOIN FETCH j.utilisateurs WHERE j.valeur = :valeur AND j.desactiver = :desactiver AND j.expired = :expired")
    Optional<Jwt> findByValeurAndDesactiverAndExpired(@Param("valeur") String valeur, @Param("desactiver") boolean desactiver, @Param("expired") boolean expired);

    @Query("FROM Jwt j WHERE j.utilisateurs.email = :email")
    Stream<Jwt> findUtilisateurs(String email);

//    @Query("FROM Jwt j WHERE j.refreshToken.valeur = :valeur")
//    Optional<Jwt> findByRefreshToken(String valeur);

//    @Query("FROM Jwt j WHERE j.expired = :expired AND j.desactiver = :desactiver AND j.utilisateurs.email= :email")
//    Optional<Jwt> findUtilisateursValidToken(String email, boolean desactiver, boolean expired);

    void deleteAllByExpiredAndDesactiver(boolean expired, boolean desactiver);

//    Optional<Jwt> findUtilisateurValidToken( boolean b, boolean b1);
}
