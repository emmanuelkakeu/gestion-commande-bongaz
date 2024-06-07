package gestion.commandeProduit.validator;

import java.util.ArrayList;
import java.util.List;

import gestion.commandeProduit.DTO.ArticleDto;
import org.springframework.util.StringUtils;

public class ArticleValidator {

    public static List<String> validate(ArticleDto dto) {
        List<String> errors = new ArrayList<>();

        if (dto == null) {
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la designation de l'article");
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
            errors.add("Veuillez renseigner le taux TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");

            return errors;
        }

        if (!StringUtils.hasLength(dto.getCodeArticle())) {
            errors.add("Veuillez renseigner le code de l'article");
        }
        if (!StringUtils.hasLength(dto.getDesignation())) {
            errors.add("Veuillez renseigner la designation de l'article");
        }
        if (dto.getPrixUnitaireHt() == null) {
            errors.add("Veuillez renseigner le prix unitaire HT l'article");
        }
        if (dto.getTauxTva() == null) {
            errors.add("Veuillez renseigner le taux TVA de l'article");
        }
        if (dto.getPrixUnitaireTtc() == null) {
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }

        return errors;
    }

}
