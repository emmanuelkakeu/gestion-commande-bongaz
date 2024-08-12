package gestion.commandeProduit.controller;

import gestion.commandeProduit.DTO.CommandeCompaniesFinalDto;
import gestion.commandeProduit.controller.api.CommandeCompaniesFinalApi;
import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import gestion.commandeProduit.service.CommandeCompaniesFinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/gestionCommande/v1")
public class CommandeCompaniesFinalControllerImpl implements CommandeCompaniesFinalApi {

    private final CommandeCompaniesFinalService commandeCompaniesFinalService;

    @Autowired
    public CommandeCompaniesFinalControllerImpl(CommandeCompaniesFinalService commandeCompaniesFinalService) {
        this.commandeCompaniesFinalService = commandeCompaniesFinalService;
    }

    @Override
    public ResponseEntity<CommandeCompaniesFinalDto> createCommande(CommandeCompaniesFinalDto commandeDto) {
        CommandeCompaniesFinalDto createdCommande = commandeCompaniesFinalService.createCommande(commandeDto);
        return  ResponseEntity.ok(createdCommande);
    }

    @Override
    public ResponseEntity<List<CommandeCompaniesFinalDto>> getAllCommandes() {
        List<CommandeCompaniesFinalDto> commandes = commandeCompaniesFinalService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @Override
    public ResponseEntity<CommandeCompaniesFinalDto> getCommandeById(Integer id) {
        return  ResponseEntity.ok(commandeCompaniesFinalService.getCommandeById(id));
    }

    @Override
    public ResponseEntity<CommandeCompaniesFinalDto> updateCommande(@PathVariable Integer id, CommandeCompaniesFinalDto commandeDto) {
        try {
            CommandeCompaniesFinalDto updatedCommande = commandeCompaniesFinalService.updateCommande(id, commandeDto);
            return ResponseEntity.ok(updatedCommande);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void   deleteCommande(@PathVariable Integer id) {
       commandeCompaniesFinalService.deleteCommandeById(id);
    }
}