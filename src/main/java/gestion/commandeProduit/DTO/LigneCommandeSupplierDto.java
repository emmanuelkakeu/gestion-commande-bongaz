package gestion.commandeProduit.DTO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.LigneCommandeSupplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeSupplierDto {

    private Integer id;

    private ArticleDto article;
    @JsonIgnore
    private CommandeSupplier commandeSupplier;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;



    public static LigneCommandeSupplierDto fromEntity(LigneCommandeSupplier ligneCommandeSupplier) {
        if (ligneCommandeSupplier == null) {
            return null;
        }
        return LigneCommandeSupplierDto.builder()
                .id(ligneCommandeSupplier.getId())
                .article(ArticleDto.fromEntity(ligneCommandeSupplier.getArticle()))
                .quantite(ligneCommandeSupplier.getQuantite())
                .prixUnitaire(ligneCommandeSupplier.getPrixUnitaire())
                .build();
    }

    public static LigneCommandeSupplier toEntity(LigneCommandeSupplierDto dto) {
        if (dto == null) {
            return null;
        }

        LigneCommandeSupplier ligneCommandeSupplier = new LigneCommandeSupplier();
        ligneCommandeSupplier.setId(dto.getId());
        ligneCommandeSupplier.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeSupplier.setPrixUnitaire(dto.getPrixUnitaire());
        ligneCommandeSupplier.setQuantite(dto.getQuantite());
        return ligneCommandeSupplier;
    }

}
