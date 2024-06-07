package gestion.commandeProduit.entities;

import java.time.Instant;

import jakarta.persistence.*;
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
@Table(name = "ventes")
public class Ventes extends AbstractEntity {

    @Column(name = "code")
    private String code;


    @Column(name = "datevente")
    private Instant dateVente;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "idSupplier")
    @JoinColumn
    private Integer idSupplier;

    @OneToMany(mappedBy = "vente")
    private List<LigneVentes> ligneVentes;



}
