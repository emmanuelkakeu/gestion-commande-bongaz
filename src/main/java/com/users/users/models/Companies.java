package com.users.users.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class Companies extends Users{

    private String openingHours;
//    @OneToMany(mappedBy = "companies")
//    private List<CommandeCompanies> commandeCompanies;
}
