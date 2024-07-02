package com.users.users.dto;


import com.users.users.models.DeliveryPerson;
import com.users.users.models.IndividualClient;
import com.users.users.models.Role;
import com.users.users.models.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class DeliveryPersonDto {


    private Integer id;

    private String name;

    private String firstName;

    private String email;

    private AdresseDto adresseDto;

    private String contactDetails;

    private String imageFileName;

    private Status status;

    private String password;

    private Role role;

    private double latitude;

    private double longitude;


    public static DeliveryPersonDto fromEntity(DeliveryPerson deliveryPerson) {
        if (deliveryPerson == null) {
            return null;
        }
        return DeliveryPersonDto.builder()
                .id(deliveryPerson.getId())
                .name(deliveryPerson.getName())
                .firstName(deliveryPerson.getFirstName())
                .adresseDto(AdresseDto.fromEntity(deliveryPerson.getAdresse()))
                .imageFileName(deliveryPerson.getImageFileName())
                .contactDetails(deliveryPerson.getContactDetails())
                .status(deliveryPerson.getStatus())
                .email(deliveryPerson.getEmail())
                .password(deliveryPerson.getPassword())
                .role(deliveryPerson.getRole())
                .latitude(deliveryPerson.getLatitude())
                .longitude(deliveryPerson.getLongitude())
                .build();
    }

    public static DeliveryPerson toEntity(DeliveryPersonDto dto) {
        if (dto == null) {
            return null;
        }
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setId(dto.getId());
        deliveryPerson.setName(dto.getName());
        deliveryPerson.setFirstName(dto.getFirstName());
        deliveryPerson.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        deliveryPerson.setImageFileName(dto.getImageFileName());
        deliveryPerson.setContactDetails(dto.getContactDetails());
        deliveryPerson.setStatus(dto.getStatus());
        deliveryPerson.setEmail(dto.getEmail());
        deliveryPerson.setPassword(dto.getPassword());
        deliveryPerson.setRole(dto.getRole() );
        deliveryPerson.setLatitude(dto.getLatitude());
        deliveryPerson.setLongitude(dto.getLongitude());


        return deliveryPerson;
    }
}