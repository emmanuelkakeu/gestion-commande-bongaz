package gestion.commandeProduit.controller;

import java.math.BigDecimal;
import java.util.List;

import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.controller.api.CommandeSupplierApi;
import gestion.commandeProduit.entities.enums.EtatCommande;
import gestion.commandeProduit.service.CommandeSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandeSupplierController implements CommandeSupplierApi {

    private final CommandeSupplierService commandeSupplierService;


    @Autowired
    public CommandeSupplierController(CommandeSupplierService commandeSupplierService) {
        this.commandeSupplierService = commandeSupplierService;

    }

    @Override
    public CommandeSupplierDto save(CommandeSupplierDto dto) {
        System.out.println("Received CommandeSupplierDto:::::::::: " + dto);
        return commandeSupplierService.save(dto);
    }

    @Override
    public CommandeSupplierDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        return commandeSupplierService.updateEtatCommande(idCommande, etatCommande);
    }

    @Override
    public CommandeSupplierDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        return commandeSupplierService.updateQuantiteCommande(idCommande, idLigneCommande, quantite);
    }

    @Override
    public CommandeSupplierDto updateSupplier(Integer idCommande, Integer idFournisseur) {
        return commandeSupplierService.updateSupplier(idCommande, idFournisseur);
    }

    @Override
    public CommandeSupplierDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        return commandeSupplierService.updateArticle(idCommande, idLigneCommande, idArticle);
    }

    @Override
    public CommandeSupplierDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        return commandeSupplierService.deleteArticle(idCommande, idLigneCommande);
    }

    @Override
    public CommandeSupplierDto findById(Integer id) {
        return commandeSupplierService.findById(id);
    }

    @Override
    public CommandeSupplierDto findByCode(String code) {
        return commandeSupplierService.findByCode(code);
    }

    @Override
    public List<CommandeSupplierDto> findAll() {
        return commandeSupplierService.findAll();
    }

    @Override
    public List<LigneCommandeSupplierDto> findAllLignesCommandesSupplierByCommandeSupplierId(Integer idCommande) {
        return commandeSupplierService.findAllLignesCommandesSupplierByCommandeSupplierId(idCommande);
    }

    @Override
    public void delete(Integer id) {
        commandeSupplierService.delete(id);
    }
}