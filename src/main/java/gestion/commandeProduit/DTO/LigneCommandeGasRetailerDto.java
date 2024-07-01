package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gestion.commandeProduit.entities.CommandeGasRetailer;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.LigneCommandeGasRetailer;
import gestion.commandeProduit.entities.LigneCommandeSupplier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeGasRetailerDto {

    private Integer id;

    private ArticleDto article;

    private BigDecimal quantite;


    @JsonIgnore
    private CommandeGasRetailer commandeGasRetailer;

    private BigDecimal prixTotalLgn;


    public static LigneCommandeGasRetailerDto fromEntity(LigneCommandeGasRetailer ligneCommandeGasRetailer) {
        if (ligneCommandeGasRetailer == null) {
            return null;
        }
        return LigneCommandeGasRetailerDto.builder()
                .id(ligneCommandeGasRetailer.getId())
                .article(ArticleDto.fromEntity(ligneCommandeGasRetailer.getArticle()))
                .quantite(ligneCommandeGasRetailer.getQuantite())
                .prixTotalLgn(ligneCommandeGasRetailer.getPrixTotalLgn())
                .build();
    }

    public static LigneCommandeGasRetailer toEntity(LigneCommandeGasRetailerDto dto) {
        if (dto == null) {
            return null;
        }

        LigneCommandeGasRetailer ligneCommandeGasRetailer = new LigneCommandeGasRetailer();
        ligneCommandeGasRetailer.setId(dto.getId());
        ligneCommandeGasRetailer.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeGasRetailer.setPrixTotalLgn(dto.getPrixTotalLgn());
        ligneCommandeGasRetailer.setQuantite(dto.getQuantite());
        return ligneCommandeGasRetailer;
    }

}

