package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.LigneCommandeCompanies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface LigneCommandeCompaniesRepository extends JpaRepository<LigneCommandeCompanies, Integer> {
    List<LigneCommandeCompanies> findAllByCommandeCompaniesId(Integer id);
}
