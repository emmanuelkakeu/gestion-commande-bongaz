package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.LigneVentes;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneVenteDto {

    private Integer id;

    private VentesDto vente;

    private ArticleDto article;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;



    public static LigneVenteDto fromEntity(LigneVentes ligneVentes) {
        if (ligneVentes == null) {
            return null;
        }

        return LigneVenteDto.builder()
                .id(ligneVentes.getId())
                .vente(VentesDto.fromEntity(ligneVentes.getVente()))
                .article(ArticleDto.fromEntity(ligneVentes.getArticle()))
                .quantite(ligneVentes.getQuantite())
                .prixUnitaire(ligneVentes.getPrixUnitaire())

                .build();
    }

    public static LigneVentes toEntity(LigneVenteDto dto) {
        if (dto == null) {
            return null;
        }
        LigneVentes ligneVentes = new LigneVentes();
        ligneVentes.setId(dto.getId());
        ligneVentes.setVente(VentesDto.toEntity(dto.getVente()));
        ligneVentes.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneVentes.setQuantite(dto.getQuantite());
        ligneVentes.setPrixUnitaire(dto.getPrixUnitaire());

        return ligneVentes;
    }

}
