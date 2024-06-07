package gestion.commandeProduit.entities.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Roles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    COMPANY("ROLE_COMPANY"),
    LIVREUR("ROLE_LIVREUR");

    private final String roleName;

    Roles(String roleName) {
        this.roleName = roleName;
    }


    public static List<Roles> fromStringList(List<String> roles) {
        return Arrays.stream(values())
                .filter(userRole -> roles.contains(userRole.getRoleName()))
                .toList();
    }
}
