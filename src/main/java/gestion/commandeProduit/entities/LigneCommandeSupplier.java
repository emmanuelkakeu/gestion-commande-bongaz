package gestion.commandeProduit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ligneCommandeSupplier")
public class LigneCommandeSupplier extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idcommandefournisseur")
    private CommandeSupplier commandeSupplier;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixTotalLgn")
    private BigDecimal prixTotalLgn;


}
