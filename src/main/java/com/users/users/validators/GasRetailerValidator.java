package com.users.users.validators;

import com.users.users.dto.CompaniesDto;
import com.users.users.dto.GasRetailerDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GasRetailerValidator {

    public static List<String> validate(GasRetailerDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du vendeur de gaz");
            errors.add("Veuillez renseigner le numero de telephone du vendeur de gaz");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if (!StringUtils.hasLength(dto.getName())) {
            errors.add("Veuillez renseigner le nom du vendeur de gaz");
        }

        if (!StringUtils.hasLength(dto.getContactDetails())) {
            errors.add("Veuillez renseigner le numero de telephone du vendeur de gaz");
        }

        errors.addAll(AdresseValidator.validate(dto.getAdresseDto()));

        return errors;
    }

}
