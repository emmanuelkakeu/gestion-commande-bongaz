package com.users.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.users.users.models.enums.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeRole libelle;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<Utilisateurs> utilisateurs;

    public Role(TypeRole typeRole) {
        this.libelle = typeRole;
    }


}
