package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CommandeGasRetailerDto;
import gestion.commandeProduit.DTO.LigneCommandeGasRetailerDto;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface CommandeGasRetailerService {

    CommandeGasRetailerDto save(CommandeGasRetailerDto dto);

    CommandeGasRetailerDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeGasRetailerDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeGasRetailerDto updateGasRetailer(Integer idCommande, Integer idFournisseur);

    CommandeGasRetailerDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Delete article ==> delete LigneCommandeGasRetailer
    CommandeGasRetailerDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeGasRetailerDto findById(Integer id);

    CommandeGasRetailerDto findByCode(String code);

    List<CommandeGasRetailerDto> findAll();

    List<LigneCommandeGasRetailerDto> findAllLignesCommandesGasRetailerByCommandeGasRetailerId(Integer idCommande);

    List<LigneCommandeGasRetailerDto> findHistoriqueCommandeGasRetailer(Integer idArticle);


    void delete(Integer id);

}
