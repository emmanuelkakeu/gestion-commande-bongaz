package gestion.commandeProduit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
public class InfosCompaniesClient {


        @Column(name = "name")
        private String name;

        @Column(name = "contactDetails")
        private String contactDetails;

        @Column(name = "openingHours")
        private String openingHours;

        @Column(name = "latitude")
        private double latitude;

        @Column(name = "longitude")
        private double longitude;



}
