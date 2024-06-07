package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.Companies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {
}
