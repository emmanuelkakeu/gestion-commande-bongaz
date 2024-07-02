package com.users.users.dto;

import com.users.users.models.Role;
import com.users.users.models.enums.Status;
import com.users.users.models.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private int id;
    private String firstName;
    private String name;
    private String lastName;
    private String email;
    private AdresseDto adresseDto;
    private String password;
    private String contactDetails;
    private Status status;
    private Date dateCreated;
    private String imageFileName;
    private Role role;
    private String typeUtilisateur;
    private double latitude;
    private double longitude;

    public static UtilisateurDto fromEntity(Utilisateurs utilisateurs) {
        if (utilisateurs == null) {
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateurs.getId())
                .name(utilisateurs.getName())
                .firstName(utilisateurs.getFirstName())
                .lastName(utilisateurs.getLastName())
                .email(utilisateurs.getEmail())
                .adresseDto(AdresseDto.fromEntity(utilisateurs.getAdresse()))
                .imageFileName(utilisateurs.getImageFileName())
                .status(utilisateurs.getStatus())
                .contactDetails(utilisateurs.getContactDetails())
                .role(utilisateurs.getRole())
                .latitude(utilisateurs.getLatitude())
                .longitude(utilisateurs.getLongitude())
                .build();
    }

    public static Utilisateurs toEntity(UtilisateurDto dto) {
        if (dto == null) {
            return null;
        }
        Utilisateurs users = new Utilisateurs();
        users.setId(dto.getId());
        users.setName(dto.getName());
        users.setFirstName(dto.getFirstName());
        users.setLastName(dto.getLastName());
        users.setEmail(dto.getEmail());
        users.setPassword(dto.getPassword());
        users.setAdresse(AdresseDto.toEntity(dto.getAdresseDto()));
        users.setImageFileName(dto.getImageFileName());
        users.setStatus(dto.getStatus());
        users.setContactDetails(dto.getContactDetails());
        users.setRole(dto.getRole());
        users.setLatitude(dto.getLatitude());
        users.setLongitude(dto.getLongitude());
        return users;
    }

}