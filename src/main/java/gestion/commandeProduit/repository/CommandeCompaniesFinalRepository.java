package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeCompaniesFinalRepository extends JpaRepository<CommandeCompaniesFinal, Integer> {
}
