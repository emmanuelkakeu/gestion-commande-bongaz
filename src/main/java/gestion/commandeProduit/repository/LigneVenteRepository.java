package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.LigneVentes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVentes, Integer> {
    List<LigneVentes> findAllByArticleId(Integer id);

    List<LigneVentes> findAllByVenteId(Integer id);
}
