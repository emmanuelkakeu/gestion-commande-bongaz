package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.DTO.CommandeSupplierDto;
import gestion.commandeProduit.DTO.CompaniesDto;
import gestion.commandeProduit.DTO.IndividualClientDto;
import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.exception.EntityNotFoundException;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.exception.InvalidOperationException;
import gestion.commandeProduit.repository.CommandeCompaniesRepository;
import gestion.commandeProduit.repository.CompaniesRepository;
import gestion.commandeProduit.repository.IndividualClientRepository;
import gestion.commandeProduit.repository.SupplierRepository;
import gestion.commandeProduit.service.CompaniesService;
import gestion.commandeProduit.validator.CompaniesValidator;
import gestion.commandeProduit.validator.IndividualClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CompaniesServiceImpl implements CompaniesService {


    private final CompaniesRepository companiesRepository;
    private final CommandeCompaniesRepository commandeCompaniesRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public CompaniesServiceImpl( CompaniesRepository companiesRepository, CommandeCompaniesRepository commandeCompaniesRepository, SupplierRepository supplierRepository) {

        this.companiesRepository = companiesRepository;
        this.commandeCompaniesRepository = commandeCompaniesRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public CompaniesDto save(CompaniesDto dto) {
        List<String> errors = CompaniesValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("companies is not valid {}", dto);
            throw new InvalidEntityException("Le companies n'est pas valide", ErrorCodes.COMPANIES_NOT_VALID, errors);
        }

        return CompaniesDto.fromEntity(
                companiesRepository.save(
                       CompaniesDto.toEntity(dto)
                )
        );
    }

    @Override
    public CompaniesDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return companiesRepository.findById(id)
                .map(CompaniesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<CompaniesDto> findAll() {
        return companiesRepository.findAll().stream()
                .map(CompaniesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        List<CommandeCompanies> commandeCompanies = commandeCompaniesRepository.findAllByCompaniesId(id);
        if (!commandeCompanies.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer l'entrepriseCliente qui a deja des commande ",
                    ErrorCodes.COMPANIES_ALREADY_IN_USE);
        }
        companiesRepository.deleteById(id);

    }
}
