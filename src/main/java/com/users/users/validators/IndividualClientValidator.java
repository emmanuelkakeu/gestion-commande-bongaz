package com.users.users.validators;


import com.users.users.dto.IndividualClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IndividualClientValidator {

    public static List<String> validate(IndividualClientDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner l'image' du client");
            errors.add("Veuillez renseigner le prenom du client");
            errors.add("Veuillez renseigner le Mail du client");
            errors.add("Veuillez renseigner le numero de telephone du client");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.hasLength(dto.getName())) {
            errors.add("Veuillez renseigner le nom du client");
        }

        if (!StringUtils.hasLength(dto.getImageFileName())) {
            errors.add("Veuillez renseigner l'image' du client");
        }


        if (!StringUtils.hasLength(dto.getFirstName())) {
            errors.add("Veuillez renseigner le prenom du client");
        }

        if (!StringUtils.hasLength(dto.getContactDetails())) {
            errors.add("Veuillez renseigner le numero de telephone du client");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresseDto()));
        return errors;
    }

}
