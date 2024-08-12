package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.CommandeCompaniesFinalDto;
import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.repository.CommandeCompaniesFinalRepository;
import gestion.commandeProduit.repository.CommandeCompaniesRepository;
import gestion.commandeProduit.service.CommandeCompaniesFinalService;
import gestion.commandeProduit.service.CommandeIndividualClientService;
import gestion.commandeProduit.validator.AdresseLivraisonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeCompaniesFinalServiceImpl implements CommandeCompaniesFinalService {

    private final CommandeCompaniesFinalRepository commandeCompaniesFinalRepository;
    private final CommandeCompaniesRepository commandeCompaniesRepository;

    @Autowired
    public CommandeCompaniesFinalServiceImpl(CommandeCompaniesFinalRepository commandeCompaniesFinalRepository, CommandeCompaniesRepository commandeCompaniesRepository) {
        this.commandeCompaniesFinalRepository = commandeCompaniesFinalRepository;
        this.commandeCompaniesRepository = commandeCompaniesRepository;
    }

    @Override
    public CommandeCompaniesFinalDto createCommande(CommandeCompaniesFinalDto dto) {

        List<String> errors = AdresseLivraisonValidator.validate(dto.getAdresseLivraison());
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("L'adresse de livraison du client n'est pas valide", ErrorCodes.ADRESSE_LIVRAISON_CLIENT_NOT_VALID, errors);
        }
        validateCommande(dto);
        if (dto.getCommandeCompaniesDto() != null) {
            commandeCompaniesRepository.save( CommandeCompaniesDto.toEntities(dto.getCommandeCompaniesDto()) );
        }

        CommandeCompaniesFinal savedCommande = commandeCompaniesFinalRepository.save(CommandeCompaniesFinalDto.toEntity(dto));
        return CommandeCompaniesFinalDto.fromEntity(savedCommande);
    }

    @Override
    public List<CommandeCompaniesFinalDto> getAllCommandes() {
        return commandeCompaniesFinalRepository.findAll().stream()
                .map(CommandeCompaniesFinalDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeCompaniesFinalDto getCommandeById(Integer id) {
        return commandeCompaniesFinalRepository.findById(id)
                .map(CommandeCompaniesFinalDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande final client n'a ete trouve avec l'ID " + id, ErrorCodes.COMMANDE_INDIVIDUALCLIENT_NOT_FOUND
                ));

    }

    @Override
    public CommandeCompaniesFinalDto updateCommande(Integer id, CommandeCompaniesFinalDto dto) {
        Optional<CommandeCompaniesFinal> existingCommandeOpt = commandeCompaniesFinalRepository.findById(id);
        if (existingCommandeOpt.isEmpty()) {
            throw new IllegalArgumentException("CommandeCompanies with ID " + id + " not found");
        }
        CommandeCompaniesFinalDto existingCommande = CommandeCompaniesFinalDto.fromEntity(existingCommandeOpt.get()) ;
        updateCommandeDetails(existingCommande, dto);
        CommandeCompaniesFinal updatedCommande = commandeCompaniesFinalRepository.save(CommandeCompaniesFinalDto.toEntity(existingCommande));
        return  CommandeCompaniesFinalDto.fromEntity(updatedCommande);
    }

    @Override
    public void deleteCommandeById(Integer id) {
        Optional<CommandeCompaniesFinal> existingCommandeOpt = commandeCompaniesFinalRepository.findById(id);
        if (existingCommandeOpt.isEmpty()) {
            throw new IllegalArgumentException("CommandeCompanies with ID " + id + " not found");
        }
        commandeCompaniesFinalRepository.deleteById(id);
    }

    private void validateCommande(CommandeCompaniesFinalDto commande) {
        if (commande.getCommandeCompaniesDto() == null) {
            throw new IllegalArgumentException("CommandeCompanies cannot be null");
        }
    }

    private void updateCommandeDetails(CommandeCompaniesFinalDto existingCommande, CommandeCompaniesFinalDto newDetails) {
        existingCommande.setCommandeCompaniesDto(newDetails.getCommandeCompaniesDto());
        existingCommande.setInfosCompaniesClient(newDetails.getInfosCompaniesClient());
        existingCommande.setAdresseLivraison(newDetails.getAdresseLivraison());
        existingCommande.setCoutLivraison(newDetails.getCoutLivraison());
        existingCommande.setCoutFinal(newDetails.getCoutFinal());
        existingCommande.setStatusCommandeFinal(newDetails.getStatusCommandeFinal());
    }
}
