package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeGasRetailer;
import gestion.commandeProduit.entities.CommandeIndividualClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandeGasRetailerRepository extends JpaRepository<CommandeGasRetailer, Integer> {
     Optional<CommandeGasRetailer> findCommandeGasRetailerByCode(String code);
}
