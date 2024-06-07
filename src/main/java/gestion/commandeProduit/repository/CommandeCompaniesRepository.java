package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeCompaniesRepository extends JpaRepository<CommandeCompanies, Integer> {


    List<CommandeCompanies> findAllByCompaniesId(Integer id);

    Optional<CommandeCompanies> findCommandeCompaniesByCode(String code);
}
