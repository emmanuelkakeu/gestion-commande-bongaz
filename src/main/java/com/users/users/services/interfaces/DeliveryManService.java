package com.users.users.services.interfaces;

import com.users.users.dto.DeliveryPersonDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DeliveryManService {

    DeliveryPersonDto save(DeliveryPersonDto dto, MultipartFile imageFile) throws IOException;

    List<DeliveryPersonDto> getAllDeliveryPersonsByVille(String ville);

    DeliveryPersonDto findById(Integer id);

    List<DeliveryPersonDto> findAll();

    void delete(Integer id);

}
