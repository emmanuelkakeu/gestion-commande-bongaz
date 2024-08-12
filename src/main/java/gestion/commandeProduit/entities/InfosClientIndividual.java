package gestion.commandeProduit.entities;

import gestion.commandeProduit.entities.enums.StatusCommandeFinal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class InfosClientIndividual  {


    @Column(name = "firstName")
    private String firstName;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "contactDetails")
    private String contactDetails;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

}
