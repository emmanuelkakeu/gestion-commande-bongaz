package com.users.users.dto;


import com.users.users.models.Role;
import com.users.users.models.enums.Status;
import lombok.Builder;
import lombok.Data;
import com.users.users.models.IndividualClient;

import java.util.List;
import java.util.stream.Collectors;

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

    private Status status;

    private String email;

    private String password;

    private Role role;

    private double latitude;

    private double longitude;



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
                .status(individualClient.getStatus())
                .email(individualClient.getEmail())
                .password(individualClient.getPassword())
                .role(individualClient.getRole())
                .latitude(individualClient.getLatitude())
                .longitude(individualClient.getLongitude())
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
        individualClient.setStatus(dto.getStatus());
        individualClient.setEmail(dto.getEmail());
        individualClient.setPassword(dto.getPassword());
        individualClient.setRole(dto.getRole());
        individualClient.setLatitude(dto.getLatitude());
        individualClient.setLongitude(dto.getLongitude());


        return individualClient;
    }

}