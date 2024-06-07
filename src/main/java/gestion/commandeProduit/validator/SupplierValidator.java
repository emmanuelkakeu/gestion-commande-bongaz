package gestion.commandeProduit.validator;

import java.util.ArrayList;
import java.util.List;

import gestion.commandeProduit.DTO.SupplierDto;
import org.springframework.util.StringUtils;


public class SupplierValidator {

    public static List<String> validate(SupplierDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prenom du fournisseur");
            errors.add("Veuillez renseigner le Mail du fournisseur");
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if (!StringUtils.hasLength(dto.getName())) {
            errors.add("Veuillez renseigner le nom du fournisseur");
        }
        if (!StringUtils.hasLength(dto.getFirstName())) {
            errors.add("Veuillez renseigner le prenom du fournisseur");
        }
       
        if (!StringUtils.hasLength(dto.getContactDetails())) {
            errors.add("Veuillez renseigner le numero de telephone du fournisseur");
        }
        errors.addAll(AdresseValidator.validate(dto.getAdresse()));
        return errors;
    }

}