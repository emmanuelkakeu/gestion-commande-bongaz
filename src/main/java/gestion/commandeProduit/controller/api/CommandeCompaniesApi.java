package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.LigneCommandeCompaniesDto;
import gestion.commandeProduit.entities.enums.EtatCommande;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;

//@Api("commandesCompanies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "commande Companies Management", description = "Endpoint to manage commande Companies")

@RequestMapping(APP_ROOT)
public interface CommandeCompaniesApi {


    @PostMapping( "/commandesCompanies/create")
    ResponseEntity<CommandeCompaniesDto> save(@RequestBody CommandeCompaniesDto dto);

    @PatchMapping( "/commandesCompanies/update/etat/{idCommande}/{etatCommande}")
    ResponseEntity<CommandeCompaniesDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping( "/commandesCompanies/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
    ResponseEntity<CommandeCompaniesDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
                                                             @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping( "/commandesCompanies/update/client/{idCommande}/{idClient}")
    ResponseEntity<CommandeCompaniesDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping( "/commandesCompanies/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
    ResponseEntity<CommandeCompaniesDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
                                                    @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping( "/commandesCompanies/delete/article/{idCommande}/{idLigneCommande}")
    ResponseEntity<CommandeCompaniesDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

    @GetMapping( "/commandesCompanies/{idCommandeCompanies}")
    ResponseEntity<CommandeCompaniesDto> findById(@PathVariable Integer idCommandeClient);

    @GetMapping( "/commandesCompanies/filter/{codeCommandeCompanies}")
    ResponseEntity<CommandeCompaniesDto> findByCode(@PathVariable("codecommandesCompanies") String code);

    @GetMapping( "/commandesCompanies/all")
    ResponseEntity<List<CommandeCompaniesDto>> findAll();

    @GetMapping( "/commandesCompanies/lignesCommande/{idCommande}")
    ResponseEntity<List<LigneCommandeCompaniesDto>> findAllLignesCommandesCompaniesByCommandeCompaniesId(@PathVariable("idCommande") Integer idCommande);


    @DeleteMapping( "/commandesCompanies/delete/{idcommandesCompanies}")
    ResponseEntity<Void> delete(@PathVariable("idcommandesCompanies") Integer id);

}