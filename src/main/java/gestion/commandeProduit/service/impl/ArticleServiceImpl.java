package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import gestion.commandeProduit.entities.LigneCommandeSupplier;
import gestion.commandeProduit.entities.LigneVentes;
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

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository;
    private final LigneCommandeSupplierRepository ligneCommandeSupplierRepository;
    private final LigneVenteRepository ligneVenteRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository, LigneCommandeSupplierRepository ligneCommandeSupplierRepository, LigneVenteRepository ligneVenteRepository) {
        this.articleRepository = articleRepository;
        this.ligneCommandeIndividualClientRepository = ligneCommandeIndividualClientRepository;
        this.ligneCommandeSupplierRepository = ligneCommandeSupplierRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }


    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
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
        List<LigneCommandeIndividualClient> ligneCommandeClients = ligneCommandeIndividualClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes client", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneCommandeSupplier> ligneCommandeFournisseurs = ligneCommandeSupplierRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseur",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneVentes> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
                    ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteById(id);
    }

}
