package gestion.commandeProduit.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "nameArticle")
    private String nameArticle;

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

    @Column(name = "stock")
    private BigDecimal stock;

    @Column(name = "image_file_name")
    private String imageFileName;

    @Column(name = "supplier_id", nullable = true)
    private Integer supplierId;

    @Column(name = "gas_retailer_id", nullable = true)
    private Integer gasRetailerId;

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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleImage> imageArticle = new ArrayList<>();
}
