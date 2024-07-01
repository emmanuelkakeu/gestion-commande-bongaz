package gestion.commandeProduit.entities;

import gestion.commandeProduit.entities.enums.EtatCommande;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandeCompanies")
public class CommandeCompanies extends AbstractEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "datecommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @Column(name = "Id_companies")
    private int idCompanies;

    @OneToMany(mappedBy = "commandeCompanies")
    private List<LigneCommandeCompanies> ligneCommandeCompanies;

    @Column(name = "prixTolalCmmd")
    private String prixTolalCmmd;



}
