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

    private BigDecimal quantite;

    private BigDecimal prixTotalLgn;

    @JsonIgnore
    private CommandeSupplier commandeSupplier;




    public static LigneCommandeSupplierDto fromEntity(LigneCommandeSupplier ligneCommandeSupplier) {
        if (ligneCommandeSupplier == null) {
            return null;
        }
        return LigneCommandeSupplierDto.builder()
                .id(ligneCommandeSupplier.getId())
                .article(ArticleDto.fromEntity(ligneCommandeSupplier.getArticle()))
                .quantite(ligneCommandeSupplier.getQuantite())
                .prixTotalLgn(ligneCommandeSupplier.getPrixTotalLgn())
                .build();
    }

    public static LigneCommandeSupplier toEntity(LigneCommandeSupplierDto dto) {
        if (dto == null) {
            return null;
        }

        LigneCommandeSupplier ligneCommandeSupplier = new LigneCommandeSupplier();
        ligneCommandeSupplier.setId(dto.getId());
        ligneCommandeSupplier.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeSupplier.setPrixTotalLgn(dto.getPrixTotalLgn());
        ligneCommandeSupplier.setQuantite(dto.getQuantite());
        return ligneCommandeSupplier;
    }

}
