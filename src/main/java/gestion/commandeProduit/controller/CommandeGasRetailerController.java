package gestion.commandeProduit.controller;

import gestion.commandeProduit.DTO.CommandeGasRetailerDto;
import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneCommandeGasRetailerDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.controller.api.CommandeGasRetailerApi;
import gestion.commandeProduit.controller.api.CommandeSupplierApi;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.service.CommandeGasRetailerService;
import gestion.commandeProduit.service.CommandeSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeGasRetailerController implements CommandeGasRetailerApi {

    private final CommandeGasRetailerService commandeGasRetailerService;


    @Autowired
    public CommandeGasRetailerController(CommandeGasRetailerService commandeGasRetailerService) {
        this.commandeGasRetailerService = commandeGasRetailerService;

    }

    @Override
    public CommandeGasRetailerDto save(CommandeGasRetailerDto dto) {

        return commandeGasRetailerService.save(dto);
    }

    @Override
    public CommandeGasRetailerDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeGasRetailerService.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeGasRetailerDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeGasRetailerService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeGasRetailerDto updateGasRetailer(Integer idCommande, Integer idFournisseur) {
        return commandeGasRetailerService.updateGasRetailer(idCommande, idFournisseur);
    }

    @Override
    public CommandeGasRetailerDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return commandeGasRetailerService.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeGasRetailerDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeGasRetailerService.deleteArticle(idCommande, idLigneCommande);
    }

    @Override
    public CommandeGasRetailerDto findById(Integer id) {
        return commandeGasRetailerService.findById(id);
    }

    @Override
    public CommandeGasRetailerDto findByCode(String code) {
        return commandeGasRetailerService.findByCode(code);
    }

    @Override
    public List<CommandeGasRetailerDto> findAll() {
        return commandeGasRetailerService.findAll();
    }

    @Override
    public List<LigneCommandeGasRetailerDto> findAllLignesCommandesGasRetailerByCommandeGasRetailerId(Integer idCommande) {
        return commandeGasRetailerService.findAllLignesCommandesGasRetailerByCommandeGasRetailerId(idCommande);
    }

    @Override
    public void delete(Integer id) {
        commandeGasRetailerService.delete(id);
    }
}