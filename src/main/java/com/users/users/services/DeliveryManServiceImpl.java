package com.users.users.services;

import com.users.users.dto.DeliveryPersonDto;
import com.users.users.exception.EntityNotFoundException;
import com.users.users.exception.ErrorCodes;
import com.users.users.exception.InvalidEntityException;
import com.users.users.repository.DeliveryManRepository;
import com.users.users.services.interfaces.DeliveryManService;
import com.users.users.validators.DeliveryManValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeliveryManServiceImpl implements DeliveryManService {

    private final DeliveryManRepository deliveryManRepository;
    private final FileService fileService;

    @Autowired
    public DeliveryManServiceImpl(DeliveryManRepository deliveryManRepository, FileService fileService) {
        this.deliveryManRepository = deliveryManRepository;
        this.fileService = fileService;
    }


    @Override
    public DeliveryPersonDto save(DeliveryPersonDto dto, MultipartFile imageFile) throws IOException {
        List<String> errors = DeliveryManValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        String imageFileName = fileService.saveImage(imageFile);
        dto.setImageFileName(imageFileName);


        return DeliveryPersonDto.fromEntity(
                deliveryManRepository.save(
                        DeliveryPersonDto.toEntity(dto)
                )
        );
    }

    @Override
    public DeliveryPersonDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return deliveryManRepository.findById(id)
                .map(DeliveryPersonDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<DeliveryPersonDto> findAll() {
        return deliveryManRepository.findAll().stream()
                .map(DeliveryPersonDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }
        deliveryManRepository.deleteById(id);
    }


}
