package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gestion.commandeProduit.entities.Article;
import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.LigneCommandeCompanies;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeCompaniesDto {

    private int id;

    private ArticleDto articleDto;

    @JsonIgnore
    private CommandeCompaniesDto commandeCompaniesDto;

    private BigDecimal quantite;

    private BigDecimal prixTotalLgn;


    public static LigneCommandeCompaniesDto fromEntities(LigneCommandeCompanies ligneCommandeCompanies){

        if (ligneCommandeCompanies == null){
            return null;
        }
         return LigneCommandeCompaniesDto.builder()
                 .id(ligneCommandeCompanies.getId())
                 .articleDto(ArticleDto.fromEntity(ligneCommandeCompanies.getArticle()))
                 .quantite(ligneCommandeCompanies.getQuantite())
                 .prixTotalLgn(ligneCommandeCompanies.getPrixTotalLgn())
                 .commandeCompaniesDto(CommandeCompaniesDto.fromEntities(ligneCommandeCompanies.getCommandeCompanies()))
                 .build();
    }

    public static LigneCommandeCompanies toEntities(LigneCommandeCompaniesDto dto){

        if (dto == null){
            return null;
        }

        LigneCommandeCompanies ligneCommandeCompanies = new LigneCommandeCompanies();
        ligneCommandeCompanies.setId(dto.getId());
        ligneCommandeCompanies.setArticle(ArticleDto.toEntity(dto.getArticleDto()));
        ligneCommandeCompanies.setQuantite(dto.getQuantite());
        ligneCommandeCompanies.setPrixTotalLgn(dto.getPrixTotalLgn());
        return ligneCommandeCompanies;
    }
}
