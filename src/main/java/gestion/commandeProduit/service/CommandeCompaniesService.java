package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CommandeCompaniesDto;
import gestion.commandeProduit.DTO.LigneCommandeCompaniesDto;
import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;
import java.util.List;


@Service
public interface CommandeCompaniesService {

    CommandeCompaniesDto save(CommandeCompaniesDto dto);

    CommandeCompaniesDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    CommandeCompaniesDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    CommandeCompaniesDto updateClient(Integer idCommande, Integer idClient);

    CommandeCompaniesDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle);

    // Delete article ==> delete LigneCommandeClient
    CommandeCompaniesDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    CommandeCompaniesDto findById(Integer id);

    CommandeCompaniesDto findByCode(String code);

    List<CommandeCompaniesDto> getCommandesByEtat(EtatCommande etatCommande);

    List<CommandeCompaniesDto> findAll();

    List<LigneCommandeCompaniesDto> findAllLignesCommandesCompaniesByCommandeCompaniesId(Integer idCommande);

    List<LigneCommandeCompaniesDto> findHistoriqueCommandeCompanies(Integer idArticle);

    void delete(Integer idCommandeCompany);


}
