package com.users.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.users.users.models.Role;
import com.users.users.models.enums.TypeRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer id;
    private TypeRole libelle;

    @JsonIgnore
    private UtilisateurDto utilisateurDto;

    public static RoleDto fromEntity(Role role) {
        if (role == null) {
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                .libelle(role.getLibelle())
                .build();
    }

    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        Role role = new Role();
        role.setId(dto.getId());
        role.setLibelle(dto.getLibelle());
        return role;
    }
}
