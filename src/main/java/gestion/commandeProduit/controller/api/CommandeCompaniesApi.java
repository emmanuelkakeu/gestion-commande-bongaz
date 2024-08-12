package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.LigneCommandeCompaniesDto;
import gestion.commandeProduit.entities.enums.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.*;



@Tag(name = "Commande Companies Management", description = "Endpoint to manage commande Companies")


public interface CommandeCompaniesApi {


    @PostMapping(CREATE_COMMANDE_COMPANIES_ENDPOINT)
    ResponseEntity<CommandeCompaniesDto> save(@RequestBody CommandeCompaniesDto dto);

    @PatchMapping(COMMANDE_COMPANIES_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeCompaniesDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(COMMANDE_COMPANIES_ENDPOINT+"/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeCompaniesDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                                @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping( COMMANDE_COMPANIES_ENDPOINT+"/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeCompaniesDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping( COMMANDE_COMPANIES_ENDPOINT+"/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeCompaniesDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(COMMANDE_COMPANIES_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeCompaniesDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(FIND_COMMANDE_COMPANIES_BY_ID_ENDPOINT )
    ResponseEntity<CommandeCompaniesDto> findById(@PathVariable Integer idCommandeClient);

    @GetMapping(FIND_COMMANDE_COMPANIES_BY_CODE_ENDPOINT )
    ResponseEntity<CommandeCompaniesDto> findByCode(@PathVariable("codecommandesCompanies") String code);

    @GetMapping(FIND_COMMANDE_COMPANIES_BY_ETAT_ENDPOINT )
    ResponseEntity<List<CommandeCompaniesDto>> getCommandesByEtat(@PathVariable("etat") EtatCommande etatCommande);

    @GetMapping(FIND_ALL_COMMANDE_COMPANIES_ENDPOINT )
    ResponseEntity<List<CommandeCompaniesDto>> findAll();

    @GetMapping( COMMANDE_COMPANIES_ENDPOINT+"/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeCompaniesDto>> findAllLignesCommandesCompaniesByCommandeCompaniesId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(DELETE_COMMANDE_COMPANIES_ENDPOINT)
    void delete(@PathVariable("idCommandeCompanies") Integer id);

}