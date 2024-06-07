package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gestion.commandeProduit.entities.Adresse;
import gestion.commandeProduit.entities.Companies;
import gestion.commandeProduit.entities.enums.Status;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class CompaniesDto {

    private Integer id;

    private String name;

    private String email;

    private AdresseDto adresseDto;

    private String contactDetails;

    private String imageFileName;



    @JsonIgnore
    private List<CommandeCompaniesDto> CommandeCompanies;

    public static CompaniesDto fromEntity(Companies companies){

        if (companies == null){
                return null;
        }

        return CompaniesDto.builder()
                    .id(companies.getId())
                    .name(companies.getName())
                    .email(companies.getEmail())
                    .adresseDto(AdresseDto.fromEntity(companies.getAdresse()))
                    .contactDetails(companies.getContactDetails())
                    .imageFileName(companies.getImageFileName())
                    .build();

    }

    public  static  Companies toEntity(CompaniesDto dto){

        if (dto == null){
            return null;
        }

        Companies companies = new Companies();

        companies.setId(dto.getId());
        companies.setName(dto.getName());
        companies.setEmail(dto.getEmail());
        companies.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        companies.setContactDetails(dto.getContactDetails());
        companies.setImageFileName(companies.getImageFileName());

        return companies;

    }

}
