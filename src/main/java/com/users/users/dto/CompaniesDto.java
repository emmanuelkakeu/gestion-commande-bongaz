package com.users.users.dto;


import lombok.Builder;
import lombok.Data;
import com.users.users.models.Companies;

import java.util.List;

@Data
@Builder
public class CompaniesDto {

    private Integer id;

    private String name;


    private AdresseDto adresseDto;

    private String contactDetails;

    private String imageFileName;

    private String openingHours;



//    @JsonIgnore
//    private List<CommandeCompaniesDto> CommandeCompanies;

    public static CompaniesDto fromEntity(Companies companies){

        if (companies == null){
                return null;
        }

        return CompaniesDto.builder()
                    .id(companies.getId())
                    .name(companies.getName())
                    .adresseDto(AdresseDto.fromEntity(companies.getAdresse()))
                    .contactDetails(companies.getContactDetails())
                    .openingHours(companies.getOpeningHours())
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
        companies.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        companies.setContactDetails(dto.getContactDetails());
        companies.setOpeningHours(dto.getOpeningHours());
        companies.setImageFileName(companies.getImageFileName());

        return companies;

    }

}
