package com.users.users.services;

import com.users.users.dto.CompaniesDto;
import com.users.users.dto.GasRetailerDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.repository.CompaniesRepository;
import com.users.users.repository.RetailerRepository;
import com.users.users.services.interfaces.CompaniesService;
import com.users.users.services.interfaces.GasRetailerService;
import com.users.users.validators.CompaniesValidator;
import com.users.users.validators.GasRetailerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GasRetailerServiceImpl implements GasRetailerService {


    private final RetailerRepository retailerRepository;
    private final FileService fileService;



    @Autowired
    public GasRetailerServiceImpl( RetailerRepository retailerRepository, FileService fileService) {
        this.retailerRepository = retailerRepository;
        this.fileService = fileService;
    }

    @Override
    public GasRetailerDto save(GasRetailerDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = GasRetailerValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("companies is not valid {}", dto);
            throw new InvalidEntityException("Le companies n'est pas valide", ErrorCodes.COMPANIES_NOT_VALID, errors);
        }

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);

        return GasRetailerDto.fromEntity(
                retailerRepository.save(
                        GasRetailerDto.toEntity(dto)
                )
        );
    }

    @Override
    public GasRetailerDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return retailerRepository.findById(id)
                .map(GasRetailerDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<GasRetailerDto> findAll() {
        return retailerRepository.findAll().stream()
                .map(GasRetailerDto::fromEntity)
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
        retailerRepository.deleteById(id);

    }
}
