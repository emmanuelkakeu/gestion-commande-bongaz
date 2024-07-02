package com.users.users.dto;

import com.users.users.models.Role;
import com.users.users.models.enums.Status;
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

    private AdresseDto adresseDto;

    private Status status;

    private Date dateCreated;

    private Instant creationDate;

    private String imageFileName;

    private String email;

    private String password;

    private Role role;

    private double latitude;

    private double longitude;

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
                .adresseDto(AdresseDto.fromEntity(supplier.getAdresse()))
                .imageFileName(supplier.getImageFileName())
                .contactDetails(supplier.getContactDetails())
                .status(supplier.getStatus())
                .email(supplier.getEmail())
                .password(supplier.getPassword())
                .role(supplier.getRole() )
                .latitude(supplier.getLatitude())
                .longitude(supplier.getLongitude())
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
        supplier.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        supplier.setImageFileName(dto.getImageFileName());
        supplier.setContactDetails(dto.getContactDetails());
        supplier.setStatus(dto.getStatus());
        supplier.setEmail(dto.getEmail());
        supplier.setPassword(dto.getPassword());
        supplier.setRole(dto.getRole());
        supplier.setLatitude(dto.getLatitude());
        supplier.setLongitude(dto.getLongitude());

        return supplier;
    }
}
