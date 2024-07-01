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
import gestion.commandeProduit.repository.ArticleRepository;
import gestion.commandeProduit.repository.CommandeIndividualClientRepository;
import gestion.commandeProduit.repository.LigneCommandeIndividualClientRepository;
import gestion.commandeProduit.service.CommandeIndividualClientService;
import gestion.commandeProduit.service.MvtStkService;
import lombok.extern.slf4j.Slf4j;
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
public class CommandeIndividualClientServiceImpl implements CommandeIndividualClientService {


    private final ArticleRepository articleRepository;
    private final LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository;
    private final CommandeIndividualClientRepository commandeIndividualClientRepository;
    private final MvtStkService mvtStkService;

    public CommandeIndividualClientServiceImpl(ArticleRepository articleRepository, LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository, CommandeIndividualClientRepository commandeIndividualClientRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.ligneCommandeIndividualClientRepository = ligneCommandeIndividualClientRepository;
        this.commandeIndividualClientRepository = commandeIndividualClientRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeIndividualClientDto save(CommandeIndividualClientDto dto) {
        log.info("Starting to save the order with details: {}", dto);

        if (dto.isCommandeLivree()) {
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }

        List<String> articleErrors = new ArrayList<>();

        if (dto.getLigneCommandeIndividualClientDto() != null) {
            dto.getLigneCommandeIndividualClientDto().forEach(ligCmdClt -> {
                if (ligCmdClt.getArticleDto() != null) {
                    log.info("Checking if article with ID {} exists in the database", ligCmdClt.getArticleDto().getId());
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticleDto().getId());
                    if (article.isEmpty()) {
                        articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticleDto().getId() + " n'existe pas");
                    } else {
                        Article articleEntity = article.get();
                        BigDecimal newStock = articleEntity.getStock().subtract(ligCmdClt.getQuantite());
                        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
                            articleErrors.add("Stock insuffisant pour l'article " + ligCmdClt.getArticleDto().getNameArticle());
                        } else {
                            articleEntity.setStock(newStock);
                            articleRepository.save(articleEntity);
                        }
                    }
                } else {
                    articleErrors.add("Impossible d'enregister une commande avec un aticle NULL");
                }
            });
        }

        if (!articleErrors.isEmpty()) {
            log.warn("Article validation errors: {}", articleErrors);
            throw new InvalidEntityException("Article n'existe pas dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        dto.setDateCommande(Instant.now());
        CommandeIndividualClient savedCmdClt = commandeIndividualClientRepository.save(CommandeIndividualClientDto.toEntity(dto));

        if (dto.getLigneCommandeIndividualClientDto() != null) {
            List<LigneCommandeIndividualClient> ligneCommandeIndividualClients = new ArrayList<>();
            for (LigneCommandeIndividualClientDto ligCmdCltDto : dto.getLigneCommandeIndividualClientDto()) {
                LigneCommandeIndividualClient ligneCommandeIndividualClient = LigneCommandeIndividualClientDto.toEntity(ligCmdCltDto);
                ligneCommandeIndividualClient.setCommandeIndividualClient(savedCmdClt);
                LigneCommandeIndividualClient savedLigneCmd = ligneCommandeIndividualClientRepository.save(ligneCommandeIndividualClient);
                ligneCommandeIndividualClients.add(savedLigneCmd);
                effectuerSortie(savedLigneCmd);
            }
            savedCmdClt.setLigneCommandeIndividualClients(ligneCommandeIndividualClients);
        }

        return CommandeIndividualClientDto.fromEntity(savedCmdClt);
    }


    @Override
    public CommandeIndividualClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
            log.error("L'etat de la commande client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
        CommandeIndividualClientDto commandeClient = checkEtatCommande(idCommande);
        commandeClient.setEtatCommande(etatCommande);

        CommandeIndividualClient savedCmdClt = commandeIndividualClientRepository.save(CommandeIndividualClientDto.toEntity(commandeClient));
        if (commandeClient.isCommandeLivree()) {
            updateMvtStk(idCommande);
        }

        return CommandeIndividualClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeIndividualClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeIndividualClientDto commandeIndividualClientDto = checkEtatCommande(idCommande);
        Optional<LigneCommandeIndividualClient> ligneCommandeIndividualClientOptional = findLigneCommandeGasRetailer(idLigneCommande);

        LigneCommandeIndividualClient ligneCommandeIndividualClient = ligneCommandeIndividualClientOptional.get();
        ligneCommandeIndividualClient.setQuantite(quantite);
        ligneCommandeIndividualClientRepository.save(ligneCommandeIndividualClient);

        return commandeIndividualClientDto;
    }



    private Optional<LigneCommandeIndividualClient> findLigneCommandeGasRetailer(Integer idLigneCommande) {

        Optional<LigneCommandeIndividualClient> ligneCommandeIndividualClientOptional = ligneCommandeIndividualClientRepository.findById(idLigneCommande);
        if (ligneCommandeIndividualClientOptional.isEmpty()) {
            throw new EntityNotFoundException(
                    "Aucune ligne commande fournisseur n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }
        return ligneCommandeIndividualClientOptional;
    }

    @Override
    public CommandeIndividualClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande IndividualClient ID is NULL");
            return null;
        }
        return commandeIndividualClientRepository.findById(id)
                .map(CommandeIndividualClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande individualClient n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_INDIVIDUALCLIENT_NOT_FOUND
                ));
    }

    @Override
    public List<CommandeIndividualClientDto> findAll() {
        return commandeIndividualClientRepository.findAll().stream()
                .map(CommandeIndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeIndividualClientDto> findAllLignesCommandesIndividualClientByCommandeIndividualClientId(Integer idCommande) {
        return ligneCommandeIndividualClientRepository.findAllByCommandeIndividualClientId(idCommande).stream()
                .map(LigneCommandeIndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeIndividualClientDto> findHistoriqueCommandeIndividualClient(Integer idArticle) {
        return ligneCommandeIndividualClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeIndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

    }

    private CommandeIndividualClientDto checkEtatCommande(Integer idCommande) {
    CommandeIndividualClientDto commandeClient = findById(idCommande);
    if (commandeClient.isCommandeLivree()) {
        throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
    }
    return commandeClient;
}

private Optional<LigneCommandeIndividualClient> findLigneCommandeIndividualClient(Integer idLigneCommande) {
    Optional<LigneCommandeIndividualClient> ligneCommandeClientOptional = ligneCommandeIndividualClientRepository.findById(idLigneCommande);
    if (ligneCommandeClientOptional.isEmpty()) {
        throw new EntityNotFoundException(
                "Aucune ligne commande client n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_INDIVIDUALCLIENT_NOT_FOUND);
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
    List<LigneCommandeIndividualClient> ligneCommandeClients = ligneCommandeIndividualClientRepository.findAllByCommandeIndividualClientId(idCommande);
    ligneCommandeClients.forEach(this::effectuerSortie);
}

private void effectuerSortie(LigneCommandeIndividualClient lig) {
    MvtStkDto mvtStkDto = MvtStkDto.builder()
            .articleDto(ArticleDto.fromEntity(lig.getArticle()))
            .dateMvt(Instant.now())
            .typeMvt(TypeMvtStk.SORTIE)
            .sourceMvt(SourceMvtStk.COMMANDE_INDIVIDUALCLIENT)
            .quantite(lig.getQuantite())

            .build();
    mvtStkService.sortieStock(mvtStkDto);
}

    @Override
    public List<CommandeIndividualClientDto> getCommandesByEtat(EtatCommande etatCommande) {

        return commandeIndividualClientRepository.findByEtatCommande(etatCommande)
                .stream()
                .map(CommandeIndividualClientDto::fromEntity)
                .collect(Collectors.toList());

    }
}
