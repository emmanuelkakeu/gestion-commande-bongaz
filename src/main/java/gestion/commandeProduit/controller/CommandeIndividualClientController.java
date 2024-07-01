package gestion.commandeProduit.controller;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.CommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeCompaniesDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.controller.api.CommandeIndividualClientApi;
import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.service.CommandeIndividualClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeIndividualClientController implements CommandeIndividualClientApi {

    private final CommandeIndividualClientService commandeIndividualClientService;

    @Autowired
    public CommandeIndividualClientController(CommandeIndividualClientService commandeIndividualClientService) {
        this.commandeIndividualClientService = commandeIndividualClientService;
    }

    @Override
    public ResponseEntity<CommandeIndividualClientDto> save(CommandeIndividualClientDto dto) {

        System.out.println("Received CommandeIndividualClientDto::::::::: " + dto);
        return ResponseEntity.ok(commandeIndividualClientService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeIndividualClientDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeIndividualClientService.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeIndividualClientDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeIndividualClientService.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

//    @Override
//    public ResponseEntity<CommandeIndividualClientDto> updateClient(Integer idCommande, Integer idClient) {
//        return ResponseEntity.ok(commandeIndividualClientService.updateClient(idCommande, idClient));
//    }

//    @Override
//    public ResponseEntity<CommandeIndividualClientDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
//        return ResponseEntity.ok(commandeIndividualClientService.updateArticle(idCommande, idLigneCommande, idArticle));
//    }

//    @Override
//    public ResponseEntity<CommandeIndividualClientDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
//        return ResponseEntity.ok(commandeIndividualClientService.deleteArticle(idCommande, idLigneCommande));
//    }

    @Override
    public ResponseEntity<CommandeIndividualClientDto> findById(Integer idCommandeCompany) {
        return ResponseEntity.ok(commandeIndividualClientService.findById(idCommandeCompany));
    }

//    @Override
//    public ResponseEntity<CommandeIndividualClientDto> findByCode(String code) {
//        return ResponseEntity.ok(commandeIndividualClientService.findByCode(code));
//    }
    @Override
    public ResponseEntity<List<CommandeIndividualClientDto>>  getCommandesByEtat(EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeIndividualClientService.getCommandesByEtat(etatCommande));
    }

    @Override
    public ResponseEntity<List<CommandeIndividualClientDto>> findAll() {
        return ResponseEntity.ok(commandeIndividualClientService.findAll());
    }

    @Override
    public ResponseEntity<List<LigneCommandeIndividualClientDto>> findAllLignesCommandesIndividualClientByCommandeIndividualClientId(Integer idCommande) {
        return ResponseEntity.ok(commandeIndividualClientService.findAllLignesCommandesIndividualClientByCommandeIndividualClientId(idCommande));
    }


    @Override
    public void delete(Integer id) {
        commandeIndividualClientService.delete(id);
    }
}
