package gestion.commandeProduit.repository;


import gestion.commandeProduit.entities.IndividualClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualClientRepository extends JpaRepository<IndividualClient, Integer> {
}
