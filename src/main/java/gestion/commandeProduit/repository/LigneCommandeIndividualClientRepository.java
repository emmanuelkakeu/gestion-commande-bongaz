package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface LigneCommandeIndividualClientRepository extends JpaRepository<LigneCommandeIndividualClient, Integer> {
   List<LigneCommandeIndividualClient> findAllByArticleId(Integer idArticle);

    List<LigneCommandeIndividualClient> findAllByCommandeIndividualClientId(Integer idCommande);



}
