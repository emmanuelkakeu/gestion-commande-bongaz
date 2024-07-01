package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.Article;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Builder
@Data
public class ArticleDto {

    private Integer id;

    private String nameArticle;

    private String codeArticle;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal tauxTva;

    private BigDecimal prixUnitaireTtc;

    private BigDecimal stockInit;

    private String imageFileName;

    private Integer supplierId;

    private Integer gasRetailerId;






    public static ArticleDto fromEntity(Article article) {
        if (article == null) {
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .nameArticle(article.getNameArticle())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .imageFileName(article.getImageFileName())
                .stockInit(article.getStockInit())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .gasRetailerId(article.getGasRetailerId())
                .supplierId(article.getSupplierId())
                .tauxTva(article.getTauxTva())
                .build();
    }

    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setNameArticle(articleDto.getNameArticle()); // Correction ici
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setImageFileName(articleDto.getImageFileName());
        article.setStockInit(articleDto.getStockInit());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setTauxTva(articleDto.getTauxTva());
        article.setGasRetailerId(articleDto.getGasRetailerId());
        article.setSupplierId(articleDto.getSupplierId());
        return article;
    }


}
