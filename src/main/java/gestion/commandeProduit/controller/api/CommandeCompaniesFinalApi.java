package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.CommandeCompaniesFinalDto;
import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.COMMANDE_COMPANIES_FINAL_ENDPOIND;


@Tag(name = "Commande Final Companies Management", description = "Endpoint to manage commande final companies")
public interface CommandeCompaniesFinalApi {

    @PostMapping(COMMANDE_COMPANIES_FINAL_ENDPOIND + "/create")
    ResponseEntity<CommandeCompaniesFinalDto> createCommande(@RequestBody CommandeCompaniesFinalDto commandeDto);

    @GetMapping(COMMANDE_COMPANIES_FINAL_ENDPOIND + "/all")
    ResponseEntity<List<CommandeCompaniesFinalDto>> getAllCommandes();

    @GetMapping(COMMANDE_COMPANIES_FINAL_ENDPOIND + "/{idCommandeFinal}")
    ResponseEntity<CommandeCompaniesFinalDto> getCommandeById(@PathVariable Integer idCommandeFinal);

    @PutMapping(COMMANDE_COMPANIES_FINAL_ENDPOIND + "/update/{idCommandeFinal}")
    ResponseEntity<CommandeCompaniesFinalDto> updateCommande(@PathVariable Integer idCommandeFinal, @RequestBody CommandeCompaniesFinalDto commandeDto);

    @DeleteMapping(COMMANDE_COMPANIES_FINAL_ENDPOIND + "/{idCommandeFinal}")
    void deleteCommande(@PathVariable Integer idCommandeFinal);
}