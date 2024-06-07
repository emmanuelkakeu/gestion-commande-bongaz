package gestion.commandeProduit.validator;

import java.util.ArrayList;
import java.util.List;

import gestion.commandeProduit.DTO.CompaniesDto;
import org.springframework.util.StringUtils;

public class CompaniesValidator {

    public static List<String> validate(CompaniesDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez renseigner l'image' de l'entreprise");
            errors.add("Veuillez renseigner le Mail del'entreprise");
            errors.add("Veuillez renseigner le numero de telephone de l'entreprise");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if (!StringUtils.hasLength(dto.getName())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getImageFileName())) {
            errors.add("Veuillez renseigner l'image' de l'entreprise");
        }
        if (!StringUtils.hasLength(dto.getEmail())) {
            errors.add("Veuillez renseigner le Mail del'entreprise");
        }
        if (!StringUtils.hasLength(dto.getContactDetails())) {
            errors.add("Veuillez renseigner le numero de telephone de l'entreprise");
        }

        errors.addAll(AdresseValidator.validate(dto.getAdresseDto()));

        return errors;
    }

}
