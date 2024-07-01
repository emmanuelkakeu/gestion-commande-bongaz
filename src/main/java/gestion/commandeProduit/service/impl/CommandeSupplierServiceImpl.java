package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.*;
import gestion.commandeProduit.entities.Article;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.LigneCommandeSupplier;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.entities.enums.SourceMvtStk;
import gestion.commandeProduit.entities.enums.TypeMvtStk;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.ArticleRepository;
import gestion.commandeProduit.repository.CommandeSupplierRepository;
import gestion.commandeProduit.repository.LigneCommandeSupplierRepository;
import gestion.commandeProduit.service.CommandeSupplierService;
import gestion.commandeProduit.service.MvtStkService;
import gestion.commandeProduit.validator.ArticleValidator;
import gestion.commandeProduit.validator.CommandeSupplierValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CommandeSupplierServiceImpl implements CommandeSupplierService {


    private final ArticleRepository articleRepository;
    private final CommandeSupplierRepository commandeSupplierRepository;
    private final LigneCommandeSupplierRepository lignecommandeSupplierRepository;

    @Autowired
    private final MvtStkService mvtStkService;

    public CommandeSupplierServiceImpl(ArticleRepository articleRepository, CommandeSupplierRepository commandeSupplierRepository, LigneCommandeSupplierRepository lignecommandeSupplierRepository, MvtStkService mvtStkService) {

        this.articleRepository = articleRepository;
        this.commandeSupplierRepository = commandeSupplierRepository;
        this.lignecommandeSupplierRepository = lignecommandeSupplierRepository;
        this.mvtStkService = mvtStkService;
    }


    @Override
    public CommandeSupplierDto save(CommandeSupplierDto dto) {
        log.info("Starting to save the order with details: {}", dto);

        if (dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeSupplierDto() != null) {
            dto.getLigneCommandeSupplierDto().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getArticleDto() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticleDto().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdFrs.getArticleDto().getId() + " n'existe pas");
                    } else {
                        Article articleEntity = article.get();
                        BigDecimal newStock = articleEntity.getStock().add(ligCmdFrs.getQuantite());
                        articleEntity.setStock(newStock);
                        articleRepository.save(articleEntity);
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("Article validation errors: {}", articleErrors);
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        dto.setDateCommande(Instant.now());
        CommandeSupplier savedCmdFrs = commandeSupplierRepository.save(CommandeSupplierDto.toEntity(dto));

        if (dto.getLigneCommandeSupplierDto() != null) {
            List<LigneCommandeSupplier> ligneCommandeSuppliers = new ArrayList<>();
            for (LigneCommandeSupplierDto ligCmdFrsDto : dto.getLigneCommandeSupplierDto()) {
                LigneCommandeSupplier ligneCommandeSupplierEntity = LigneCommandeSupplierDto.toEntity(ligCmdFrsDto);
                ligneCommandeSupplierEntity.setCommandeSupplier(savedCmdFrs);
                LigneCommandeSupplier savedLigneCmd = lignecommandeSupplierRepository.save(ligneCommandeSupplierEntity);
                ligneCommandeSuppliers.add(savedLigneCmd);
                effectuerEntree(savedLigneCmd);
            }
            savedCmdFrs.setLigneCommandeSupplier(ligneCommandeSuppliers);
        }

        return CommandeSupplierDto.fromEntity(savedCmdFrs);
    }


    @Override
    public CommandeSupplierDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeSupplierDto commandeSupplierDto = checkEtatCommande(idCommande);
        commandeSupplierDto.setEtatCommande(etatCommande);

        CommandeSupplier savedCommande = commandeSupplierRepository.save(CommandeSupplierDto.toEntity(commandeSupplierDto));
        if (commandeSupplierDto.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeSupplierDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeSupplierDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeSupplierDto commandeSupplierDto = checkEtatCommande(idCommande);
        Optional<LigneCommandeSupplier> ligneCommandeSupplierOptional = findLigneCommandeSupplier(idLigneCommande);

        LigneCommandeSupplier ligneCommandeSupplier = ligneCommandeSupplierOptional.get();
        ligneCommandeSupplier.setQuantite(quantite);
        lignecommandeSupplierRepository.save(ligneCommandeSupplier);

        return commandeSupplierDto;
    }

    @Override
    public CommandeSupplierDto updateSupplier(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeSupplierDto commandeSupplierDto = checkEtatCommande(idCommande);

        return CommandeSupplierDto.fromEntity(
                commandeSupplierRepository.save(CommandeSupplierDto.toEntity(commandeSupplierDto))
        );
    }

    @Override
    public CommandeSupplierDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeSupplierDto commandeSupplierDto = checkEtatCommande(idCommande);

        Optional<LigneCommandeSupplier> ligneCommandeSupplier = findLigneCommandeSupplier(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeSupplier ligneCommandeSupplierToSaved = ligneCommandeSupplier.get();
        ligneCommandeSupplierToSaved.setArticle(articleOptional.get());
        lignecommandeSupplierRepository.save(ligneCommandeSupplierToSaved);

        return commandeSupplierDto;
    }


    @Override
    public CommandeSupplierDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeSupplierDto commandeSupplierDto = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
        findLigneCommandeSupplier(idLigneCommande);
        lignecommandeSupplierRepository.deleteById(idLigneCommande);

        return commandeSupplierDto;
    }

    @Override
    public CommandeSupplierDto findById(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return null;
        }
        return commandeSupplierRepository.findById(id)
                .map(CommandeSupplierDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeSupplierDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande fournisseur CODE is NULL");
            return null;
        }
        return commandeSupplierRepository.findCommandeSupplierByCode(code)
                .map(CommandeSupplierDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeSupplierDto> findAll() {
        return commandeSupplierRepository.findAll().stream()
                .map(CommandeSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeSupplierDto> findAllLignesCommandesSupplierByCommandeSupplierId(Integer idCommande) {
        return lignecommandeSupplierRepository.findAllByCommandeSupplierId(idCommande).stream()
                .map(LigneCommandeSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(Integer idArticle) {
        return lignecommandeSupplierRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeSupplierDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return;
        }
//        List<LigneCommandeSupplier> ligneCommandeFournisseurs = lignecommandeSupplierRepository.findAllByCommandeSupplierId(id);
//        if (!ligneCommandeFournisseurs.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
//                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
//        }
        commandeSupplierRepository.deleteById(id);

    }

    private Optional<LigneCommandeSupplier> findLigneCommandeSupplier(Integer idLigneCommande) {
        Optional<LigneCommandeSupplier> ligneCommandeFournisseurOptional = lignecommandeSupplierRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private CommandeSupplierDto checkEtatCommande(Integer idCommande) {
        CommandeSupplierDto commandeSupplierDto = findById(idCommande);
        if (commandeSupplierDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeSupplierDto;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeSupplier> ligneCommandeSuppliers = lignecommandeSupplierRepository.findAllByCommandeSupplierId(idCommande);
        ligneCommandeSuppliers.forEach(this::effectuerEntree);
    }


    private void effectuerEntree(LigneCommandeSupplier lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .articleDto(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_SUPPLIER)
                .quantite(lig.getQuantite())
                .build();
        mvtStkService.entreeStock(mvtStkDto);
    }
}