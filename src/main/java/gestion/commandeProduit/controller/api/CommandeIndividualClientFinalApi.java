package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import gestion.commandeProduit.entities.CommandeIndividualClientFinal;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.COMMANDE_COMPANIES_FINAL_ENDPOIND;
import static gestion.commandeProduit.utils.Constants.COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND;


@Tag(name = "Commande Final Individualclient Management", description = "Endpoint to manage commande final individualclient")
public interface CommandeIndividualClientFinalApi {


        @PostMapping(COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND + "/create")
        ResponseEntity<CommandeIndividualClientFinal> createCommande(@RequestBody CommandeIndividualClientFinal commande);
        @GetMapping( COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND + "/all")
        ResponseEntity<List<CommandeIndividualClientFinal>> getAllCommandes();
        @GetMapping( COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND + "/{idCommandeFinal}")
        ResponseEntity<CommandeIndividualClientFinal> getCommandeById(@PathVariable Integer idCommandeFinal);
        @PutMapping(COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND + "update/{idCommandeFinal}")
        ResponseEntity<CommandeIndividualClientFinal> updateCommande(@PathVariable Integer idCommandeFinal, @RequestBody CommandeIndividualClientFinal commande);
        @DeleteMapping( COMMANDE_INDIVIDUALCLIENT_FINAL_ENDPOIND + "/{idCommandeFinal}" )
        ResponseEntity<Void> deleteCommande(@PathVariable Integer idCommandeFinal);


}
