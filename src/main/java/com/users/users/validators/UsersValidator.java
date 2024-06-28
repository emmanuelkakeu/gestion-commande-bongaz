package com.users.users.validators;

import java.util.ArrayList;
import java.util.List;

import com.users.users.dto.UsersDto;
import org.springframework.util.StringUtils;

public class UsersValidator {

    public static List<String> validate(UsersDto usersDto) {
        List<String> errors = new ArrayList<>();

        if (usersDto == null) {
            errors.add("Veuillez renseigner le nom d'utilisateur");
            errors.add("Veuillez renseigner le prenom d'utilisateur");
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
            errors.add("Veuillez renseigner l'adresse d'utilisateur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if (!StringUtils.hasLength(usersDto.getName())) {
            errors.add("Veuillez renseigner le nom d'utilisateur");
        }
        if (!StringUtils.hasLength(usersDto.getFirstName())) {
            errors.add("Veuillez renseigner le prenom d'utilisateur");
        }
        if (!StringUtils.hasLength(usersDto.getEmail())) {
            errors.add("Veuillez renseigner l'email d'utilisateur");
        }
        if (!StringUtils.hasLength(usersDto.getPassword())) {
            errors.add("Veuillez renseigner le mot de passe d'utilisateur");
        }

        errors.addAll(AdresseValidator.validate(usersDto.getAdresseDto()));

        return errors;
    }

}
