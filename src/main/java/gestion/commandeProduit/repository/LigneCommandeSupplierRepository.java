package gestion.commandeProduit.repository;

import gestion.commandeProduit.entities.LigneCommandeSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface LigneCommandeSupplierRepository extends JpaRepository<LigneCommandeSupplier,Integer> {
    List<LigneCommandeSupplier> findAllByArticleId(Integer idArticle);

    List<LigneCommandeSupplier> findAllByCommandeSupplierId(Integer idCommande);


}
