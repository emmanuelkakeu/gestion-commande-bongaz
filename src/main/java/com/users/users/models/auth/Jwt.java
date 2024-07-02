package com.users.users.models.auth;

import com.users.users.models.Utilisateurs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jwt")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String valeur;
    private boolean desactiver;
    private boolean expired ;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private RefreshToken refreshToken;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateurs_id")
    private Utilisateurs utilisateurs;


}
