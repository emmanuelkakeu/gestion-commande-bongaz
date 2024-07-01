package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CommandeGasRetailerDto;
import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneCommandeGasRetailerDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.entities.enums.EtatCommande;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.*;



@Tag(name = "Commande GasRetailer Management", description = "Endpoint to manage commande gasRetailer")
public interface CommandeGasRetailerApi {

    @PostMapping(CREATE_COMMANDE_GASRETAILER_ENDPOINT)
    CommandeGasRetailerDto save(@RequestBody CommandeGasRetailerDto dto);

    @PatchMapping(COMMANDE_GASRETAILER_ENDPOINT + "/update/etat/{idCommande}/{etatCommande}")
    CommandeGasRetailerDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(COMMANDE_GASRETAILER_ENDPOINT + "/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    CommandeGasRetailerDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                               @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(COMMANDE_GASRETAILER_ENDPOINT + "/update/gasRetailer/{idCommande}/{idFournisseur}")
    CommandeGasRetailerDto updateGasRetailer(@PathVariable("idCommande") Integer idCommande, @PathVariable("idGasRetailer") Integer idGasRetailer);

    @PatchMapping(COMMANDE_GASRETAILER_ENDPOINT + "/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    CommandeGasRetailerDto updateArticle(@PathVariable("idCommande") Integer idCommande,
                                      @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(COMMANDE_GASRETAILER_ENDPOINT + "/delete/article/{idCommande}/{idLigneCommande}")
    CommandeGasRetailerDto deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping(FIND_COMMANDE_GASRETAILER_BY_ID_ENDPOINT)
    CommandeGasRetailerDto findById(@PathVariable("idCommandeGasRetailer") Integer id);

    @GetMapping(FIND_COMMANDE_GASRETAILER_BY_CODE_ENDPOINT)
    CommandeGasRetailerDto findByCode(@PathVariable("codeCommandeGasRetailer") String code);



    @GetMapping(FIND_ALL_COMMANDE_GASRETAILER_ENDPOINT)
    List<CommandeGasRetailerDto> findAll();

    @GetMapping(COMMANDE_GASRETAILER_ENDPOINT + "/lignesCommande/{idCommande}")
    List<LigneCommandeGasRetailerDto> findAllLignesCommandesGasRetailerByCommandeGasRetailerId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(DELETE_COMMANDE_GASRETAILER_ENDPOINT)
    void delete(@PathVariable("idCommandeGasRetailer") Integer id);

}
