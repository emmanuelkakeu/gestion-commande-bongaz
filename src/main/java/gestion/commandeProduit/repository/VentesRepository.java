package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.LigneVentes;
import gestion.commandeProduit.entities.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VentesRepository extends JpaRepository<Ventes, Integer> {


    Optional<Ventes> findVentesByCode(String code);
}
