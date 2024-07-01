package gestion.commandeProduit.entities;

import gestion.commandeProduit.DTO.ArticleDto;
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
@Table(name = "lignecommandeIndividualClient")
public class LigneCommandeIndividualClient extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idcommandeIndividualClient ")
    private CommandeIndividualClient commandeIndividualClient;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixTotalLgn")
    private BigDecimal prixTotalLgn;



}
