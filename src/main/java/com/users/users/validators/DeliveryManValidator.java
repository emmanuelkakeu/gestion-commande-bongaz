package com.users.users.validators;

import com.users.users.dto.DeliveryPersonDto;
import com.users.users.dto.IndividualClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManValidator {

    public static List<String> validate(DeliveryPersonDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du livreur");
            errors.add("Veuillez renseigner l'image' du livreur");
            errors.add("Veuillez renseigner le prenom du livreur");
            errors.add("Veuillez renseigner le Mail du livreur");
            errors.add("Veuillez renseigner le numero de telephone du livreur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }
        if (!StringUtils.hasLength(dto.getName())) {
            errors.add("Veuillez renseigner le nom du livreur");
        }

        if (!StringUtils.hasLength(dto.getImageFileName())) {
            errors.add("Veuillez renseigner l'image' du livreur");
        }


        if (!StringUtils.hasLength(dto.getFirstName())) {
            errors.add("Veuillez renseigner le prenom du livreur");
        }

        if (!StringUtils.hasLength(dto.getContactDetails())) {
            errors.add("Veuillez renseigner le mnuero de telephone du livreur");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresseDto()));
        return errors;
    }
}
