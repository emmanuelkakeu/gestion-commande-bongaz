package gestion.commandeProduit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class IndividualClient extends Users {

    @OneToMany(mappedBy = "individualClient")
    private List<CommandeIndividualClient> commandeIndividualClients;

}
