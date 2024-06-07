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
import gestion.commandeProduit.service.CommandeCompaniesService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import gestion.commandeProduit.service.MvtStkService;
import gestion.commandeProduit.validator.ArticleValidator;
import gestion.commandeProduit.validator.CommandeCompaniesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CommandeCompaniesServiceImpl implements CommandeCompaniesService {

    private final CommandeCompaniesRepository commandeCompaniesRepository;
    private final LigneCommandeCompaniesRepository ligneCommandeCompaniesRepository;
    private final CompaniesRepository companiesRepository;
    private final ArticleRepository articleRepository;
    private final MvtStkService mvtStkService;


    @Autowired
    public CommandeCompaniesServiceImpl(CommandeCompaniesRepository commandeCompaniesRepository,
                                        CompaniesRepository companiesRepository,
                                        ArticleRepository articleRepository,
                                        LigneCommandeCompaniesRepository ligneCommandeCompaniesRepository,
                                        MvtStkService mvtStkService) {
        this.commandeCompaniesRepository = commandeCompaniesRepository;
        this.ligneCommandeCompaniesRepository = ligneCommandeCompaniesRepository;
        this.companiesRepository = companiesRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;

    }

    @Override
    public CommandeCompaniesDto save(CommandeCompaniesDto dto) {

        List<String> errors = CommandeCompaniesValidator.validate(dto);

        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_ENTREPRISE_CLIENTE_NOT_VALID, errors);
        }

        if ( dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        Optional<Companies> client = companiesRepository.findById(dto.getCompaniesDto().getId());
        if (client.isEmpty()) {
            log.warn("Client with ID {} was not found in the DB", dto.getCompaniesDto().getId());
            throw new EntityNotFoundException("Aucun client avec l'ID" + dto.getCompaniesDto().getId() + " n'a ete trouve dans la BDD",
                    ErrorCodes.CLIENT_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeCompaniesDto() != null) {
            dto.getLigneCommandeCompaniesDto().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticleDto() != null) {
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticleDto().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticleDto().getId() + " n'existe pas");
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
        CommandeCompanies savedCmdClt = commandeCompaniesRepository.save(CommandeCompaniesDto.toEntities(dto));

        if (dto.getLigneCommandeCompaniesDto() != null) {
            dto.getLigneCommandeCompaniesDto().forEach(ligCmdClt -> {
                LigneCommandeCompanies ligneCommandeCompanies = LigneCommandeCompaniesDto.toEntities(ligCmdClt);
                ligneCommandeCompanies.setCommandeCompanies(savedCmdClt);
                LigneCommandeCompanies savedLigneCmd = ligneCommandeCompaniesRepository.save(ligneCommandeCompanies);

                effectuerSortie(savedLigneCmd);
            });
        }

        return CommandeCompaniesDto.fromEntities(savedCmdClt);
    }

    @Override
    public CommandeCompaniesDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            return null;
        }
        return commandeCompaniesRepository.findById(id)
                .map(CommandeCompaniesDto::fromEntities)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public CommandeCompaniesDto findByCode(String code) {
        if (!StringUtils.hasLength(code)) {
            log.error("Commande client CODE is NULL");
            return null;
        }
        return commandeCompaniesRepository.findCommandeCompaniesByCode(code)
                .map(CommandeCompaniesDto::fromEntities)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande client n'a ete trouve avec le CODE " + code, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeCompaniesDto> findAll() {
        return commandeCompaniesRepository.findAll().stream()
                .map(CommandeCompaniesDto::fromEntities)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Commande client ID is NULL");
            return;
        }
        List<LigneCommandeCompanies> ligneCommandeCompanies = ligneCommandeCompaniesRepository.findAllByCommandeCompaniesId(id);
        if (!ligneCommandeCompanies.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer une commande client deja utilisee",
                    ErrorCodes.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        commandeCompaniesRepository.deleteById(id);
    }

    @Override
    public List<LigneCommandeCompaniesDto> findHistoriqueCommandeCompanies(Integer idArticle) {
        return null;
    }

    @Override
    public List<LigneCommandeCompaniesDto> findAllLignesCommandesCompaniesByCommandeCompaniesId(Integer idCommande) {
        return ligneCommandeCompaniesRepository.findAllByCommandeCompaniesId(idCommande).stream()
                .map(LigneCommandeCompaniesDto::fromEntities)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeCompaniesDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeCompaniesDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);

        CommandeCompanies savedCmdClt = commandeCompaniesRepository.save(CommandeCompaniesDto.toEntities(commandeClient));
        if (commandeClient.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }

        return CommandeCompaniesDto.fromEntities(savedCmdClt);
    }

    @Override
    public CommandeCompaniesDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        CommandeCompaniesDto commandeClient = checkEtatCommande(idCommande);
        Optional<LigneCommandeCompanies> ligneCommandeCompaniesOptional = findLigneCommandeCompanies(idLigneCommande);

        LigneCommandeCompanies ligneCommandeCompanies = ligneCommandeCompaniesOptional.get();
        ligneCommandeCompanies.setQuantite(quantite);
        ligneCommandeCompaniesRepository.save(ligneCommandeCompanies);

        return commandeClient;
    }

    @Override
    public CommandeCompaniesDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null) {
            log.error("L'ID du client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeCompaniesDto commandeClient = checkEtatCommande(idCommande);
        Optional<Companies> companiesOptional = companiesRepository.findById(idClient);
        if (companiesOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucun client n'a ete trouve avec l'ID " + idClient, ErrorCodes.CLIENT_NOT_FOUND);
        }
        commandeClient.setCompaniesDto(CompaniesDto.fromEntity(companiesOptional.get()));

        return CommandeCompaniesDto.fromEntities(
                commandeCompaniesRepository.save(CommandeCompaniesDto.toEntities(commandeClient))
        );
    }

    @Override
    public CommandeCompaniesDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeCompaniesDto commandeCompaniesDto = checkEtatCommande(idCommande);

        Optional<LigneCommandeCompanies> ligneCommandeCompanies = findLigneCommandeCompanies(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);
        if (articleOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeCompanies ligneCommandeClientToSaved = ligneCommandeCompanies.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeCompaniesRepository.save(ligneCommandeClientToSaved);

        return commandeCompaniesDto;
    }

    @Override
    public CommandeCompaniesDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeCompaniesDto commandeClient = checkEtatCommande(idCommande);
        // Just to check the LigneCommandeClient and inform the client in case it is absent
        findLigneCommandeCompanies(idLigneCommande);
        ligneCommandeCompaniesRepository.deleteById(idLigneCommande);

        return commandeClient;
    }

    private CommandeCompaniesDto checkEtatCommande(Integer idCommande) {
        CommandeCompaniesDto commandeClient = findById(idCommande);
        if (commandeClient.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        return commandeClient;
    }

    private Optional<LigneCommandeCompanies> findLigneCommandeCompanies(Integer idLigneCommande) {
        Optional<LigneCommandeCompanies> ligneCommandeClientOptional = ligneCommandeCompaniesRepository.findById(idLigneCommande);
        if (ligneCommandeClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande client n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClientOptional;
    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
        if (idArticle == null) {
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void updateMvtStk(Integer idCommande) {
        List<LigneCommandeCompanies> ligneCommandeClients = ligneCommandeCompaniesRepository.findAllByCommandeCompaniesId(idCommande);
        ligneCommandeClients.forEach(this::effectuerSortie);
    }

    private void effectuerSortie(LigneCommandeCompanies lig) {
        MvtStkDto mvtStkDto = MvtStkDto.builder()
                .articleDto(ArticleDto.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.SORTIE)
                .sourceMvt(SourceMvtStk.COMMANDE_COMPANIES)
                .quantite(lig.getQuantite())

                .build();
        mvtStkService.sortieStock(mvtStkDto);
    }
}