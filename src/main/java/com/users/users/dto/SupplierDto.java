package com.users.users.dto;


import com.users.users.enums.Status;
import com.users.users.models.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {

    private int id;

    private String firstName;

    private String name;

    private String lastName;

    private String contactDetails;

    private AdresseDto adresse;

    private Status status;

    private Date dateCreated;

    private Instant creationDate;

    private String imageFileName;

//    @JsonIgnore
//    private List<CommandeSupplierDto> commandeSupplierDto;


    public static SupplierDto fromEntity(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .firstName(supplier.getFirstName())
                .lastName(supplier.getLastName())
                .creationDate(supplier.getCreationDate())
                .status(supplier.getStatus())
                .adresse(AdresseDto.fromEntity(supplier.getAdresse()))
                .imageFileName(supplier.getImageFileName())
                .contactDetails(supplier.getContactDetails())
                .build();
    }

    public static Supplier toEntity(SupplierDto dto) {
        if (dto == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setFirstName(dto.getFirstName());
        supplier.setLastName(dto.getLastName());
        supplier.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        supplier.setImageFileName(dto.getImageFileName());
        supplier.setContactDetails(dto.getContactDetails());

        return supplier;
    }
}
