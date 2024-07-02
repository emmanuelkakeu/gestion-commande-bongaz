package com.users.users.models;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class Supplier extends Utilisateurs {

//
//    @OneToMany(mappedBy = "supplier")
//    private List<CommandeSupplier> commandeSuppliers;



}


