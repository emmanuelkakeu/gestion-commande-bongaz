package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.*;
import gestion.commandeProduit.entities.*;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.entities.enums.SourceMvtStk;
import gestion.commandeProduit.entities.enums.TypeMvtStk;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.*;
import gestion.commandeProduit.service.CommandeGasRetailerService;
import gestion.commandeProduit.service.MvtStkService;
import gestion.commandeProduit.validator.ArticleValidator;
import gestion.commandeProduit.validator.CommandeGasRetailerValidator;
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

@Service@Slf4j
public class CommandeGasRetailerServiceImpl implements CommandeGasRetailerService {


    private final ArticleRepository articleRepository;
    private final CommandeGasRetailerRepository commandeGasRetailerRepository;
    private final LigneCommandeGasRetailerRepository ligneCommandeGasRetailerRepository;
    private final MvtStkService mvtStkService;

    @Autowired
    public CommandeGasRetailerServiceImpl( ArticleRepository articleRepository, CommandeGasRetailerRepository commandeGasRetailerRepository, LigneCommandeGasRetailerRepository ligneCommandeGasRetailerRepository, MvtStkService mvtStkService) {

        this.articleRepository = articleRepository;
        this.commandeGasRetailerRepository = commandeGasRetailerRepository;
        this.ligneCommandeGasRetailerRepository = ligneCommandeGasRetailerRepository;
        this.mvtStkService = mvtStkService;
    }


    @Override
    public CommandeGasRetailerDto save(CommandeGasRetailerDto dto) {

//        List<String> errors = CommandeGasRetailerValidator.validate(dto);
//
//        if (!errors.isEmpty()) {
//            log.error("Commande fournisseur n'est pas valide");
//            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
//        }

        if (dto.getId() != null && dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }



        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeGasRetailers() != null) {
            dto.getLigneCommandeGasRetailers().forEach(ligCmdGrl -> {
                if (ligCmdGrl.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdGrl.getArticle().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdGrl.getArticle().getId() + " n'existe pas");
                    }else {
                        Article articleEntity = article.get();
                        BigDecimal newStock = articleEntity.getStockInit().add(ligCmdGrl.getQuantite());
                        articleEntity.setStockInit(newStock);
                        articleRepository.save(articleEntity);

                    }
                } else {
                    articleErrors.add("Impossible d'enregister une commande avec un aticle NULL");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }
        dto.setDateCommande(Instant.now());
        CommandeGasRetailer savedCmdFrs = commandeGasRetailerRepository.save(CommandeGasRetailerDto.toEntity(dto));


        if (dto.getLigneCommandeGasRetailers() != null) {
           dto.getLigneCommandeGasRetailers().forEach(ligCmdGrl -> {
                LigneCommandeGasRetailer ligneCommandeGasRetailer = LigneCommandeGasRetailerDto.toEntity(LigneCommandeGasRetailerDto.fromEntity(ligCmdGrl));
                ligneCommandeGasRetailer.setCommandeGasRetailer(savedCmdFrs);
//
               LigneCommandeGasRetailer saveLigne = ligneCommandeGasRetailerRepository.save(ligneCommandeGasRetailer);

                effectuerEntree(saveLigne);
            });
        }

        return CommandeGasRetailerDto.fromEntity(savedCmdFrs);
    }

    @Override
    public CommandeGasRetailerDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeGasRetailerDto commandeGasRetailerDto = checkEtatCommande(idCommande);
        commandeGasRetailerDto.setEtatCommande(etatCommande);

        CommandeGasRetailer savedCommande = commandeGasRetailerRepository.save(CommandeGasRetailerDto.toEntity(commandeGasRetailerDto));
        if (commandeGasRetailerDto.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }
        return CommandeGasRetailerDto.fromEntity(savedCommande);
    }

    @Override
    public CommandeGasRetailerDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

       CommandeGasRetailerDto commandeGasRetailerDto = checkEtatCommande(idCommande);
        Optional<LigneCommandeGasRetailer> ligneCommandeGasRetailerOptional = findLigneCommandeGasRetailer(idLigneCommande);

        LigneCommandeGasRetailer ligneCommandeGasRetailer = ligneCommandeGasRetailerOptional.get();
        ligneCommandeGasRetailer.setQuantite(quantite);
        ligneCommandeGasRetailerRepository.save(ligneCommandeGasRetailer);

        return commandeGasRetailerDto;
    }

    @Override
    public CommandeGasRetailerDto updateGasRetailer(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("L'ID du fournisseur is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
            CommandeGasRetailerDto commandeGasRetailerDto = checkEtatCommande(idCommande);

        return CommandeGasRetailerDto.fromEntity(
               commandeGasRetailerRepository.save(CommandeGasRetailerDto.toEntity(commandeGasRetailerDto)
        ));
    }

    @Override
    public CommandeGasRetailerDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeGasRetailerDto commandeGasRetailerDto = checkEtatCommande(idCommande);

        Optional<LigneCommandeGasRetailer> ligneCommandeGasRetailer = findLigneCommandeGasRetailer(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeGasRetailer ligneCommandeGasRetailerToSaved = ligneCommandeGasRetailer.get();
        ligneCommandeGasRetailerToSaved.setArticle(articleOptional.get());
        ligneCommandeGasRetailerRepository.save(ligneCommandeGasRetailerToSaved);

        return commandeGasRetailerDto;
    }


    @Override
    public CommandeGasRetailerDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeGasRetailerDto commandeGasRetailerDto = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
        findLigneCommandeGasRetailer(idLigneCommande);
        ligneCommandeGasRetailerRepository.deleteById(idLigneCommande);

        return commandeGasRetailerDto;
    }

    @Override
    public CommandeGasRetailerDto findById(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return null;
        }
        return commandeGasRetailerRepository.findById(id)
                .map(CommandeGasRetailerDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeGasRetailerDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande fournisseur CODE is NULL");
            return null;
        }
        return commandeGasRetailerRepository.findCommandeGasRetailerByCode(code)
                .map(CommandeGasRetailerDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeGasRetailerDto> findAll() {
        return commandeGasRetailerRepository.findAll().stream()
                .map(CommandeGasRetailerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeGasRetailerDto> findAllLignesCommandesGasRetailerByCommandeGasRetailerId(Integer idCommande) {
        return ligneCommandeGasRetailerRepository.findAllByCommandeGasRetailerId(idCommande).stream()
                .map(LigneCommandeGasRetailerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeGasRetailerDto> findHistoriqueCommandeGasRetailer(Integer idArticle) {
        return ligneCommandeGasRetailerRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeGasRetailerDto :: fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Commande fournisseur ID is NULL");
            return;
        }
//        List<LigneCommandeGasRetailer> ligneCommandeGasRetailers = ligneCommandeGasRetailerRepository.findAllByCommandeGasRetailerId(id);
//        if (!ligneCommandeGasRetailers.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee",
//                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
//        }
        commandeGasRetailerRepository.deleteById(id);

    }

    private Optional<LigneCommandeGasRetailer> findLigneCommandeGasRetailer(Integer idLigneCommande) {
        Optional<LigneCommandeGasRetailer> ligneCommandeFournisseurOptional = ligneCommandeGasRetailerRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeFournisseurOptional;
    }

    private CommandeGasRetailerDto checkEtatCommande(Integer idCommande) {
        CommandeGasRetailerDto commandeGasRetailerDto = findById(idCommande);
        if (commandeGasRetailerDto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        return commandeGasRetailerDto;
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
        List<LigneCommandeGasRetailer> ligneCommandeGasRetailers = ligneCommandeGasRetailerRepository.findAllByCommandeGasRetailerId(idCommande);
        ligneCommandeGasRetailers.forEach(this::effectuerEntree);
    }


    private void effectuerEntree(LigneCommandeGasRetailer lig) {
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


