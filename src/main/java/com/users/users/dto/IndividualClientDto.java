package com.users.users.dto;


import lombok.Builder;
import lombok.Data;
import com.users.users.models.IndividualClient;

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


//    @JsonIgnore
//    private List<CommandeIndividualClientDto> CommandeIndividualClient;

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
        IndividualClient individualClient = new IndividualClient();
        individualClient.setId(dto.getId());
        individualClient.setName(dto.getName());
        individualClient.setFirstName(dto.getFirstName());
        individualClient.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        individualClient.setImageFileName(dto.getImageFileName());
        individualClient.setContactDetails(dto.getContactDetails());
        return individualClient;
    }

}