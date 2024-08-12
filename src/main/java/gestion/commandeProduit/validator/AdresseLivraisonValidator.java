package gestion.commandeProduit.validator;

import java.util.ArrayList;
import java.util.List;

import gestion.commandeProduit.entities.AdresseLivraison;
import org.springframework.util.StringUtils;


public class AdresseLivraisonValidator {

    public static List<String> validate(AdresseLivraison adresseLivraison) {
        List<String> errors = new ArrayList<>();

        if (adresseLivraison == null) {
            errors.add("Veuillez renseigner la ville de livraison'");
            errors.add("Veuillez renseigner le quartier de livraison");
            errors.add("Veuillez renseigner la rue");

            return errors;
        }
        if (!StringUtils.hasLength(adresseLivraison.getVille())) {
            errors.add("Veuillez renseigner la ville de livraison'");
        }
        if (!StringUtils.hasLength(adresseLivraison.getQuartier())) {
            errors.add("Veuillez renseigner le quartier de livraison");
        }
        if (!StringUtils.hasLength(adresseLivraison.getRue())) {
            errors.add("Veuillez renseigner la rue");
        }

        return errors;
    }

}
