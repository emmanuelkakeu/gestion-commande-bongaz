package gestion.commandeProduit.controller;

import java.math.BigDecimal;
import java.util.List;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.LigneCommandeCompaniesDto;
import gestion.commandeProduit.controller.api.CommandeCompaniesApi;
import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.service.CommandeCompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandeCompaniesController implements CommandeCompaniesApi {

    private final CommandeCompaniesService commandeCompaniesService;

    @Autowired
    public CommandeCompaniesController(CommandeCompaniesService commandeClientService) {
        this.commandeCompaniesService = commandeClientService;
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> save(CommandeCompaniesDto dto) {
        return ResponseEntity.ok(commandeCompaniesService.save(dto));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeCompaniesService.updateEtatCommande(idCommande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeCompaniesService.updateQuantiteCommande(idCommande, idLigneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> updateClient(Integer idCommande, Integer idClient) {
        return ResponseEntity.ok(commandeCompaniesService.updateClient(idCommande, idClient));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return ResponseEntity.ok(commandeCompaniesService.updateArticle(idCommande, idLigneCommande, idArticle));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return ResponseEntity.ok(commandeCompaniesService.deleteArticle(idCommande, idLigneCommande));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> findById(Integer idCommandeCompany) {
        return ResponseEntity.ok(commandeCompaniesService.findById(idCommandeCompany));
    }

    @Override
    public ResponseEntity<CommandeCompaniesDto> findByCode(String code) {
        return ResponseEntity.ok(commandeCompaniesService.findByCode(code));
    }
    @Override
    public ResponseEntity<List<CommandeCompaniesDto>>  getCommandesByEtat(EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeCompaniesService.getCommandesByEtat(etatCommande));
    }

    @Override
    public ResponseEntity<List<CommandeCompaniesDto>> findAll() {
        return ResponseEntity.ok(commandeCompaniesService.findAll());
    }

    @Override
    public ResponseEntity<List<LigneCommandeCompaniesDto>> findAllLignesCommandesCompaniesByCommandeCompaniesId(Integer idCommande) {
        return ResponseEntity.ok(commandeCompaniesService.findAllLignesCommandesCompaniesByCommandeCompaniesId(idCommande));
    }


    @Override
    public void delete(Integer id) {
        commandeCompaniesService.delete(id);
    }
}
