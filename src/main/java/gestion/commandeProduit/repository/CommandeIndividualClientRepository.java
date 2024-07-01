package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import gestion.commandeProduit.entities.enums.EtatCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommandeIndividualClientRepository extends JpaRepository<CommandeIndividualClient, Integer> {


    List<CommandeIndividualClient> findByEtatCommande(EtatCommande etatCommande);
}
