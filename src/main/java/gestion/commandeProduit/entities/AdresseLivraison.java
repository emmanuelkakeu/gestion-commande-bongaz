package gestion.commandeProduit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AdresseLivraison implements Serializable {

    @Column(name = "ville")
    private String ville;

    @Column(name = "quartier")
    private String quartier;

    @Column(name = "rue")
    private String rue;

}
