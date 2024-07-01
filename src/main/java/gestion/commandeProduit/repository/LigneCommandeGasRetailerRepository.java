package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.LigneCommandeGasRetailer;
import gestion.commandeProduit.entities.LigneCommandeSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeGasRetailerRepository extends JpaRepository<LigneCommandeGasRetailer, Integer> {

    List<LigneCommandeGasRetailer> findAllByArticleId(Integer idArticle);

    List<LigneCommandeGasRetailer> findAllByCommandeGasRetailerId(Integer idCommande);

}
