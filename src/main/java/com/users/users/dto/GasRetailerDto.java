package com.users.users.dto;

import com.users.users.enums.Status;
import com.users.users.models.Adresse;
import com.users.users.models.GasRetailer;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GasRetailerDto {

    private int id;

    private String firstName;

    private String name;

    private String lastName;

    private AdresseDto adresseDto;

    private String contactDetails;

    private Status status;

    private Date dateCreated;

    private String imageFileName;

    private String openingHours;


    public static GasRetailerDto fromEntity(GasRetailer gasRetailer){

        if (gasRetailer == null){
            return null;
        }
        return GasRetailerDto.builder()
                .id(gasRetailer.getId())
                .firstName(gasRetailer.getFirstName())
                .name(gasRetailer.getName())
                .lastName(gasRetailer.getLastName())
                .adresseDto(AdresseDto.fromEntity(gasRetailer.getAdresse()))
                .contactDetails(gasRetailer.getContactDetails())
                .status(gasRetailer.getStatus())
                .openingHours(gasRetailer.getOpeningHours())
                .imageFileName(gasRetailer.getImageFileName())
                .build();
    }

    public static  GasRetailer toEntity(GasRetailerDto dto){

        if (dto == null){
            return  null;
        }
        GasRetailer gasRetailer = new GasRetailer();
        gasRetailer.setFirstName(dto.getFirstName());
        gasRetailer.setName(dto.getName());
        gasRetailer.setLastName(dto.getLastName());
        gasRetailer.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        gasRetailer.setContactDetails(dto.getContactDetails());
        gasRetailer.setOpeningHours(dto.getOpeningHours());
        gasRetailer.setImageFileName(dto.getImageFileName());

        return gasRetailer;
    }
}
