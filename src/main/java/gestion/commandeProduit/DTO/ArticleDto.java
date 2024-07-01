package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.Article;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import gestion.commandeProduit.entities.ArticleImage;

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
    private BigDecimal stock;
    private String imageFileName;
    private Integer supplierId;
    private Integer gasRetailerId;
    private List<String> imageArticle;  // Ajoutez cette ligne

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
                .stock(article.getStock())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .gasRetailerId(article.getGasRetailerId())
                .supplierId(article.getSupplierId())
                .tauxTva(article.getTauxTva())
                .imageArticle(article.getImageArticle() != null ?
                        article.getImageArticle().stream()
                                .map(ArticleImage::getImageFileName)
                                .collect(Collectors.toList()) : null)  // Vérifiez si imageArticle est nul
                .build();
    }


    public static Article toEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setNameArticle(articleDto.getNameArticle());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setImageFileName(articleDto.getImageFileName());
        article.setStock(articleDto.getStock());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setTauxTva(articleDto.getTauxTva());
        article.setGasRetailerId(articleDto.getGasRetailerId() );

        return article;
    }

}