package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

import gestion.commandeProduit.entities.Adresse;
import gestion.commandeProduit.entities.IndividualClient;
import gestion.commandeProduit.entities.Role;
import gestion.commandeProduit.entities.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndividualClientDto {

    private int id;

    private String firstName;

    private String name;

    private String lastName;

    private AdresseDto adresseDto;

    private String contactDetails;

    private String imageFileName;


    @JsonIgnore
    private List<CommandeIndividualClientDto> CommandeIndividualClient;

    public static IndividualClientDto fromEntity(IndividualClient individualClient) {
        if (individualClient == null) {
            return null;
        }
        return IndividualClientDto.builder()
                .id(individualClient.getId())
                .name(individualClient.getName())
                .firstName(individualClient.getFirstName())
                .adresseDto(AdresseDto.fromEntity(individualClient.getAdresse()))
                .imageFileName(individualClient.getImageFileName())
                .contactDetails(individualClient.getContactDetails())
                .build();
    }

    public static IndividualClient toEntity(IndividualClientDto dto) {
        if (dto == null) {
            return null;
        }
        IndividualClient individualClientDto = new IndividualClient();
        individualClientDto.setId(dto.getId());
        individualClientDto.setName(dto.getName());
        individualClientDto.setFirstName(dto.getFirstName());
        individualClientDto.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        individualClientDto.setImageFileName(dto.getImageFileName());
        individualClientDto.setContactDetails(dto.getContactDetails());
        return individualClientDto;
    }

}