package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.CommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.entities.enums.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.*;


@Tag(name = "Commande IndividualClient Management", description = "Endpoint to manage commande IndividualClient")
public interface CommandeIndividualClientApi {


    @PostMapping(CREATE_COMMANDE_INDIVIDUALCLIENT_ENDPOINT)
    ResponseEntity<CommandeIndividualClientDto> save(@RequestBody CommandeIndividualClientDto dto);

    @PatchMapping(COMMANDE_INDIVIDUALCLIENT_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeIndividualClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(COMMANDE_INDIVIDUALCLIENT_ENDPOINT+"/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeIndividualClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                                @PathVariable("idLigneCommande") Integer idLigneCommande,
                                                                @PathVariable("quantite") BigDecimal quantite);

//    @PatchMapping( COMMANDE_INDIVIDUALCLIENT_ENDPOINT+"/update/client/{idCommande}/{idClient}")
//    ResponseEntity<CommandeIndividualClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

//    @PatchMapping( COMMANDE_INDIVIDUALCLIENT_ENDPOINT+"/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
//    ResponseEntity<CommandeIndividualClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
//                                                       @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);
//
//    @DeleteMapping(COMMANDE_INDIVIDUALCLIENT_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
//    ResponseEntity<CommandeIndividualClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(FIND_COMMANDE_INDIVIDUALCLIENT_BY_ID_ENDPOINT )
    ResponseEntity<CommandeIndividualClientDto> findById(@PathVariable Integer idCommandeClient);

//    @GetMapping(FIND_COMMANDE_INDIVIDUALCLIENT_BY_CODE_ENDPOINT )
//    ResponseEntity<CommandeIndividualClientDto> findByCode(@PathVariable("codecommandesCompanies") String code);

    @GetMapping(FIND_COMMANDE_INDIVIDUALCLIENT_BY_ETAT_ENDPOINT )
    ResponseEntity<List<CommandeIndividualClientDto>> getCommandesByEtat(@PathVariable("etat") EtatCommande etatCommande);

    @GetMapping(FIND_ALL_COMMANDE_INDIVIDUALCLIENT_ENDPOINT )
    ResponseEntity<List<CommandeIndividualClientDto>> findAll();

    @GetMapping( COMMANDE_INDIVIDUALCLIENT_ENDPOINT+"/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeIndividualClientDto>> findAllLignesCommandesIndividualClientByCommandeIndividualClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(DELETE_COMMANDE_INDIVIDUALCLIENT_ENDPOINT)
    void delete(@PathVariable("idCommandeCompanies") Integer id);

}



