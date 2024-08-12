package gestion.commandeProduit.controller;


import gestion.commandeProduit.controller.api.CommandeIndividualClientFinalApi;
import gestion.commandeProduit.entities.CommandeIndividualClientFinal;
import gestion.commandeProduit.service.CommandeIndividualClientFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gestionCommande/v1")
public class CommandeIndividualClientFinalControllerImpl implements CommandeIndividualClientFinalApi {


    private final CommandeIndividualClientFinalService commandeIndividualClientFinalService;

    @Autowired
    public CommandeIndividualClientFinalControllerImpl(CommandeIndividualClientFinalService commandeIndividualClientFinalService) {
        this.commandeIndividualClientFinalService = commandeIndividualClientFinalService;
    }

    @Override
    public ResponseEntity<CommandeIndividualClientFinal> createCommande(CommandeIndividualClientFinal commande) {
        CommandeIndividualClientFinal createdCommande = commandeIndividualClientFinalService.createCommande(commande);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CommandeIndividualClientFinal>> getAllCommandes() {
        List<CommandeIndividualClientFinal> commandes = commandeIndividualClientFinalService.getAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommandeIndividualClientFinal> getCommandeById(Integer id) {
        return commandeIndividualClientFinalService.getCommandeById(id)
                .map(commande -> new ResponseEntity<>(commande, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CommandeIndividualClientFinal> updateCommande( Integer id, CommandeIndividualClientFinal commande) {
        try {
            CommandeIndividualClientFinal updatedCommande = commandeIndividualClientFinalService.updateCommande(id, commande);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteCommande(Integer id) {
        try {
            commandeIndividualClientFinalService.deleteCommandeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

