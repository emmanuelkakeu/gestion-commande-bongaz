package gestion.commandeProduit.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import gestion.commandeProduit.entities.Adresse;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.Supplier;
import gestion.commandeProduit.entities.enums.Status;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class SupplierDto {

    private int id;

    private String code;

    private String firstName;

    private String name;

    private String lastName;

    private String contactDetails;

    private AdresseDto adresse;

    private Status status;

    private Date dateCreated;

    private String imageFileName;

    @JsonIgnore
    private List<CommandeSupplierDto> commandeSupplierDto;

    public static SupplierDto fromEntity(Supplier supplier) {
        if (supplier == null) {
            return null;
        }
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .firstName(supplier.getFirstName())
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
        supplier.setId( dto.getId());
        supplier.setName(dto.getName());
        supplier.setFirstName(dto.getFirstName());
        supplier.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        supplier.setImageFileName(dto.getImageFileName());
        supplier.setContactDetails(dto.getContactDetails());

        return supplier;
    }
}
