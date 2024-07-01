package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.CommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface CommandeIndividualClientService  {

    CommandeIndividualClientDto save(CommandeIndividualClientDto dto);

    CommandeIndividualClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeIndividualClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

//    CommandeIndividualClientDto updateGasRetailer(Integer idCommande, Integer idIndividualClient);
//
//    CommandeIndividualClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);
//

//    CommandeIndividualClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeIndividualClientDto findById(Integer id);

    List<CommandeIndividualClientDto> getCommandesByEtat(EtatCommande etatCommande);

//    CommandeIndividualClientDto findByCode(String code);

    List<CommandeIndividualClientDto> findAll();

    List<LigneCommandeIndividualClientDto> findAllLignesCommandesIndividualClientByCommandeIndividualClientId(Integer idCommande);

    List<LigneCommandeIndividualClientDto> findHistoriqueCommandeIndividualClient(Integer idArticle);


    void delete(Integer id);
}
