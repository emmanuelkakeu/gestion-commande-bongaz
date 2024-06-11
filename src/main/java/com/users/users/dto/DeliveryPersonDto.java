package com.users.users.dto;


import com.users.users.models.DeliveryPerson;
import com.users.users.models.IndividualClient;
import lombok.Builder;
import lombok.Data;

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
        return deliveryPerson;
    }
}