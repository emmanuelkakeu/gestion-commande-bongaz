package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Collection;

import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigneCommandeIndividualClientDto {

    private Integer id;

    private ArticleDto article;


    private BigDecimal quantite;

    private BigDecimal prixTotalLgn;

    @JsonIgnore
    private CommandeIndividualClientDto commandeIndividualClient;


    public static LigneCommandeIndividualClientDto fromEntity(LigneCommandeIndividualClient ligneCommandeIndividualClient) {
        if (ligneCommandeIndividualClient == null) {
            return null;
        }
        return LigneCommandeIndividualClientDto.builder()
                .id(ligneCommandeIndividualClient.getId())
                .article(ArticleDto.fromEntity(ligneCommandeIndividualClient.getArticle()))
                .quantite(ligneCommandeIndividualClient.getQuantite())
                .prixTotalLgn(ligneCommandeIndividualClient.getPrixTotalLgn())
                .build();
    }

    public static LigneCommandeIndividualClient toEntity(LigneCommandeIndividualClientDto dto) {
        if (dto == null) {
            return null;
        }

        LigneCommandeIndividualClient ligneCommandeClient = new LigneCommandeIndividualClient();
        ligneCommandeClient.setId(dto.getId());
        ligneCommandeClient.setArticle(ArticleDto.toEntity(dto.getArticle()));
        ligneCommandeClient.setPrixTotalLgn(dto.getPrixTotalLgn());
        ligneCommandeClient.setQuantite(dto.getQuantite());
        return ligneCommandeClient;
    }


}
