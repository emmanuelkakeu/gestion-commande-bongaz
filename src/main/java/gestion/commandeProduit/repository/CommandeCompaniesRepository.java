package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeCompaniesRepository extends JpaRepository<CommandeCompanies, Integer> {


    Optional<CommandeCompanies> findCommandeCompaniesByCode(String code);
    List<CommandeCompanies> findByEtatCommande(EtatCommande etatCommande);
}
