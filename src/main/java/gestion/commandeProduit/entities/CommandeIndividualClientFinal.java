package gestion.commandeProduit.entities;


import gestion.commandeProduit.entities.enums.StatusCommandeFinal;
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
@Table(name = "commandeIndividualClientFinal")
public class CommandeIndividualClientFinal extends AbstractEntity{

    @OneToOne
    @JoinColumn(name = "commandeIndividualClient_id", referencedColumnName = "id")
    private CommandeIndividualClient commandeIndividualClient;

    @Embedded
    InfosClientIndividual infosClientIndividual;

    @Embedded
    private AdresseLivraison adresseLivraison;

    private BigDecimal coutLivraison;

    private  BigDecimal coutFinal;


    @Enumerated(EnumType.STRING)
    @Column(name = "statusCommandeFinal")
    private StatusCommandeFinal statusCommandeFinal;

}

