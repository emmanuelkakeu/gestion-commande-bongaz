package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeIndividualClientFinal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeIndividualClientFinalRepository extends JpaRepository<CommandeIndividualClientFinal, Integer> {
}

