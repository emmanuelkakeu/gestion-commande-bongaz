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
@Table(name = "commandeIndividualClient")
public class CommandeIndividualClient extends AbstractEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "datecommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "Id_individualClient")
    private IndividualClient individualClient;

    @OneToMany(mappedBy = "commandeIndividualClient")
    private List<LigneCommandeIndividualClient> ligneCommandeIndividualClients;

}
