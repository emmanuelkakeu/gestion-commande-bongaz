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
@Table(name = "lignecommandeGasRetailer")
public class LigneCommandeGasRetailer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idcommandeGasRetailer")
    private CommandeGasRetailer commandeGasRetailer;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixTotalLgn")
    private BigDecimal prixTotalLgn;


}

