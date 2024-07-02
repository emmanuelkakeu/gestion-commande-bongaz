package com.users.users.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class Companies extends Utilisateurs {

    private String openingHours;
//    @OneToMany(mappedBy = "companies")
//    private List<CommandeCompanies> commandeCompanies;
}
