package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CommandeCompaniesFinalDto;
import gestion.commandeProduit.entities.CommandeCompaniesFinal;

import java.util.List;
import java.util.Optional;



public interface CommandeCompaniesFinalService {

    CommandeCompaniesFinalDto createCommande(CommandeCompaniesFinalDto commandeDto);

    List<CommandeCompaniesFinalDto> getAllCommandes();

   CommandeCompaniesFinalDto getCommandeById(Integer id);

    CommandeCompaniesFinalDto updateCommande(Integer id, CommandeCompaniesFinalDto commandeDto);

    void deleteCommandeById(Integer id);
}