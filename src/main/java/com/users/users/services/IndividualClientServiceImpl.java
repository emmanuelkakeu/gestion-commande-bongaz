package com.users.users.services;


import com.users.users.dto.IndividualClientDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.repository.IndividualClientRepository;
import com.users.users.services.interfaces.IndividualClientService;
import com.users.users.validators.IndividualClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class IndividualClientServiceImpl implements IndividualClientService {
    private final IndividualClientRepository individualClientRepository;
    private final FileService fileService;

    @Autowired
    public IndividualClientServiceImpl(IndividualClientRepository individualClientRepository, FileService fileService)
                                      {
        this.individualClientRepository = individualClientRepository;

        this.fileService = fileService;
                                      }



    @Override
    public IndividualClientDto save(IndividualClientDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = IndividualClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);

        return IndividualClientDto.fromEntity(
                individualClientRepository.save(
                        IndividualClientDto.toEntity(dto)
                )
        );
    }

    @Override
    public IndividualClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return individualClientRepository.findById(id)
                .map(IndividualClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<IndividualClientDto> findAll() {
        return individualClientRepository.findAll().stream()
                .map(IndividualClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        individualClientRepository.deleteById(id);
    }


}
