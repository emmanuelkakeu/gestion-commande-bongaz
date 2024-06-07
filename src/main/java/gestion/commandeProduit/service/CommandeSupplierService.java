package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface CommandeSupplierService {

    CommandeSupplierDto save(CommandeSupplierDto dto);

    CommandeSupplierDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeSupplierDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeSupplierDto updateSupplier(Integer idCommande, Integer idFournisseur);

    CommandeSupplierDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Delete article ==> delete LigneCommandeFournisseur
    CommandeSupplierDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeSupplierDto findById(Integer id);

    CommandeSupplierDto findByCode(String code);

    List<CommandeSupplierDto> findAll();

    List<LigneCommandeSupplierDto> findAllLignesCommandesSupplierByCommandeSupplierId(Integer idCommande);

    List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(Integer idArticle);


    void delete(Integer id);

}