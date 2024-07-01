package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import gestion.commandeProduit.entities.*;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.*;
import gestion.commandeProduit.service.ArticleService;
import gestion.commandeProduit.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
     private final LigneVenteRepository ligneVenteRepository;
    private final FileService fileService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository ligneVenteRepository, FileService fileService) {
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.fileService = fileService;
    }


    @Override
    public ArticleDto save(ArticleDto dto, MultipartFile imageFile, MultipartFile[] imageFiles) throws IOException {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        // Sauvegarde de l'image principale
        String mainImageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(mainImageFileName);

        // Sauvegarde des images supplémentaires
        List<ArticleImage> articleImages = new ArrayList<>();
        for (MultipartFile imgFile : imageFiles) {
            String imageFileName = fileService.saveImage(imgFile);
            ArticleImage articleImage = new ArticleImage();
            articleImage.setImageFileName(imageFileName);
            articleImages.add(articleImage);
        }

        Article article = ArticleDto.toEntity(dto);
        article.setImageArticle(articleImages);
        for (ArticleImage articleImage : articleImages) {
            articleImage.setArticle(article);
        }

        // Calculer le prix TTC
        BigDecimal prixUnitaireHt = dto.getPrixUnitaireHt();
        BigDecimal tauxTva = dto.getTauxTva();
        BigDecimal cent = new BigDecimal(100);

        BigDecimal prixTva = prixUnitaireHt.multiply(tauxTva).divide(cent, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP);
        BigDecimal prixTtc = prixUnitaireHt.add(prixTva);

        dto.setPrixUnitaireTtc(prixTtc);

        Article savedArticle = articleRepository.save(article);

        return ArticleDto.fromEntity(savedArticle);
    }

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID is null");
            return null;
        }

        return articleRepository.findById(id).map(ArticleDto::fromEntity).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun article avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND)
        );
    }

    @Override
    public List<ArticleDto> findBySupplierId(Integer supplierId) {
        if (supplierId == null) {
            log.error("Supplier ID is null");
            return Collections.emptyList();
        }

        return articleRepository.findBySupplierId(supplierId)
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findByGasRetailerId(Integer gasRetailerId) {
        if (gasRetailerId == null) {
            log.error("Gas Retailer ID is null");
            return Collections.emptyList();
        }

        return articleRepository.findByGasRetailerId(gasRetailerId)
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if (!StringUtils.hasLength(codeArticle)) {
            log.error("Article CODE is null");
            return null;
        }

        return articleRepository.findArticleByCodeArticle(codeArticle)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun article avec le CODE = " + codeArticle + " n' ete trouve dans la BDD",
                                ErrorCodes.ARTICLE_NOT_FOUND)
                );
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return ligneVenteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Article ID is null");
            return;
        }
//        List<LigneCommandeIndividualClient> ligneCommandeClients = ligneCommandeIndividualClientRepository.findAllByArticleId(id);
//        if (!ligneCommandeClients.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
//        }
//        List<LigneCommandeSupplier> ligneCommandeFournisseurs = ligneCommandeSupplierRepository.findAllByArticleId(id);
//        if (!ligneCommandeFournisseurs.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
//                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
//        }
//        List<LigneVentes> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
//        if (!ligneVentes.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
//                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
//        }
        articleRepository.deleteById(id);
    }

}
