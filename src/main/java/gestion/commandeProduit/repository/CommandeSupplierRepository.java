package gestion.commandeProduit.repository;

import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.entities.CommandeSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommandeSupplierRepository extends JpaRepository<CommandeSupplier,Integer> {

     Optional<CommandeSupplier> findCommandeSupplierByCode(String code);
}
