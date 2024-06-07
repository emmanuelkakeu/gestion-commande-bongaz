package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.SupplierDto;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.CommandeSupplierRepository;
import gestion.commandeProduit.repository.SupplierRepository;
import gestion.commandeProduit.service.SupplierService;
import gestion.commandeProduit.validator.SupplierValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final CommandeSupplierRepository commandeSupplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, CommandeSupplierRepository commandeSupplierRepository) {
        this.supplierRepository = supplierRepository;
        this.commandeSupplierRepository = commandeSupplierRepository;
    }

    @Override
    public SupplierDto save(SupplierDto dto) {
        List<String> errors = SupplierValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Fournisseur is not valid {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }

        return SupplierDto.fromEntity(
                supplierRepository.save(
                        SupplierDto.toEntity(dto)
                )
        );
    }

    @Override
    public SupplierDto findById(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return null;
        }
        return supplierRepository.findById(id)
                .map(SupplierDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
                );
    }

    @Override
    public List<SupplierDto> findAll() {
        return supplierRepository.findAll().stream()
                .map(SupplierDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return;
        }
        List<CommandeSupplier> commandeFournisseur = commandeSupplierRepository.findAllBySupplierId(id);
        if (!commandeFournisseur.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }
        supplierRepository.deleteById(id);
    }

}
