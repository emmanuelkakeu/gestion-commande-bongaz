package gestion.commandeProduit.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {

    @Column(name = "codearticle")
    private String codeArticle;

    @Column(name = "designation")
    private String designation;

    @Column(name = "prixunitaireht")
    private BigDecimal prixUnitaireHt;

    @Column(name = "tauxtva")
    private BigDecimal tauxTva;

    @Column(name = "prixunitairettc")
    private BigDecimal prixUnitaireTtc;

    @Column(name = "imageFileName")
    private String imageFileName;


    @OneToMany(mappedBy = "article")
    private List<LigneVentes> ligneVentes;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeIndividualClient> ligneCommandeIndividualClients;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeSupplier> ligneCommandeSuppliers;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeCompanies> ligneCommandeCompanies;


    @OneToMany(mappedBy = "article")
    private List<MvtStk> mvtStks;

}


