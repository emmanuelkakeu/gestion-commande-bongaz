package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.CommandeIndividualClientDto;
import gestion.commandeProduit.DTO.IndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.SupplierDto;
import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.IndividualClient;
import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.ArticleRepository;
import gestion.commandeProduit.repository.CommandeIndividualClientRepository;
import gestion.commandeProduit.repository.IndividualClientRepository;
import gestion.commandeProduit.repository.LigneCommandeIndividualClientRepository;
import gestion.commandeProduit.service.IndividualClientService;
import gestion.commandeProduit.validator.IndividualClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class IndividualClientServiceImpl implements IndividualClientService {
    private final IndividualClientRepository individualClientRepository;
    private final CommandeIndividualClientRepository commandeIndividualClientRepository;
    private final LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository;

    @Autowired
    public IndividualClientServiceImpl(IndividualClientRepository individualClientRepository,
                                       CommandeIndividualClientRepository commandeIndividualClientRepository,
                                       LigneCommandeIndividualClientRepository ligneCommandeIndividualClientRepository)
                                       {
        this.individualClientRepository = individualClientRepository;
        this.commandeIndividualClientRepository = commandeIndividualClientRepository;
        this.ligneCommandeIndividualClientRepository = ligneCommandeIndividualClientRepository;

    }



    @Override
    public IndividualClientDto save(IndividualClientDto dto) {
        List<String> errors = IndividualClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return IndividualClientDto.fromEntity(
                individualClientRepository.save(
                        IndividualClientDto.toEntity(dto)
                )
        );
    }

    @Override
    public IndividualClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return individualClientRepository.findById(id)
                .map(IndividualClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<IndividualClientDto> findAll() {
        return individualClientRepository.findAll().stream()
                .map(IndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List<CommandeIndividualClient> commandeClients = commandeIndividualClientRepository.findAllByIndividualClientId(id);
        if (!commandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un client qui a deja des commande clients",
                    ErrorCodes.CLIENT_ALREADY_IN_USE);
        }
        individualClientRepository.deleteById(id);
    }

    @Override
    public List<LigneCommandeIndividualClientDto> findHistoriqueCommandeIndividualClient(Integer idArticle) {
        return ligneCommandeIndividualClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeIndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }


}
