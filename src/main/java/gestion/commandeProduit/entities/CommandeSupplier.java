package gestion.commandeProduit.entities;


import java.time.Instant;
import java.util.List;

import gestion.commandeProduit.entities.enums.EtatCommande;
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
@Table(name = "commandeSupplier")
public class CommandeSupplier extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "datecommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "idSupplier")
    private Supplier supplier;

    @OneToMany(mappedBy = "commandeSupplier")
    private List<LigneCommandeSupplier> ligneCommandeSupplier;


}
