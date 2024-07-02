package com.users.users.dto;


import com.users.users.models.Role;
import com.users.users.models.enums.Status;
import lombok.Builder;
import lombok.Data;
import com.users.users.models.Companies;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CompaniesDto {

    private Integer id;

    private String name;

    private AdresseDto adresseDto;

    private String contactDetails;

    private String imageFileName;

    private Status status;

    private String openingHours;

    private String email;

    private String password;

    private Role role;

    private double latitude;

    private double longitude;

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
                .status(companies.getStatus())
                .email(companies.getEmail())
                .password(companies.getPassword())
                .role(companies.getRole())
                .latitude(companies.getLatitude())
                .longitude(companies.getLongitude())
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
        companies.setImageFileName(dto.getImageFileName());
        companies.setStatus(dto.getStatus());
        companies.setEmail(dto.getEmail());
        companies.setPassword(dto.getPassword());
        companies.setRole(dto.getRole());
        companies.setLatitude(dto.getLatitude());
        companies.setLongitude(dto.getLongitude());


        return companies;

    }

}
