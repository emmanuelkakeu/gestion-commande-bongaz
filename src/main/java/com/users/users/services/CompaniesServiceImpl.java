package com.users.users.services;


import com.users.users.dto.CompaniesDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.repository.CompaniesRepository;
import com.users.users.repository.SupplierRepository;
import com.users.users.services.interfaces.CompaniesService;
import com.users.users.validators.CompaniesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CompaniesServiceImpl implements CompaniesService {


    private final CompaniesRepository companiesRepository;
    private final FileService fileService;



    @Autowired
    public CompaniesServiceImpl(CompaniesRepository companiesRepository, FileService fileService) {

        this.companiesRepository = companiesRepository;
        this.fileService = fileService;
    }

    @Override
    public CompaniesDto save(CompaniesDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = CompaniesValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("companies is not valid {}", dto);
            throw new InvalidEntityException("Le companies n'est pas valide", ErrorCodes.COMPANIES_NOT_VALID, errors);
        }

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);

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
//        List<CommandeCompanies> commandeCompanies = commandeCompaniesRepository.findAllByCompaniesId(id);
//        if (!commandeCompanies.isEmpty()) {
//            throw new InvalidOperationException("Impossible de supprimer l'entrepriseCliente qui a deja des commande ",
//                    ErrorCodes.COMPANIES_ALREADY_IN_USE);
//        }
        companiesRepository.deleteById(id);

    }
}
