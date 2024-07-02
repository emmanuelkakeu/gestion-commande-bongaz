package com.users.users.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users.controler.api.DeliveryPersonApi;
import com.users.users.dto.DeliveryPersonDto;
import com.users.users.services.interfaces.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class DeliveryPersonControllerImpl implements DeliveryPersonApi {

    private final DeliveryManService deliveryManService;
    private final ObjectMapper objectMapper;


    @Autowired
    public DeliveryPersonControllerImpl(DeliveryManService deliveryManService, ObjectMapper objectMapper) {
        this.deliveryManService = deliveryManService;
        this.objectMapper = objectMapper;
//        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public DeliveryPersonDto save(@RequestPart("deliveryPersonDto") String deliveryPersonJson, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        DeliveryPersonDto deliveryPersonDto = objectMapper.readValue(deliveryPersonJson, DeliveryPersonDto.class);
        return deliveryManService.save(deliveryPersonDto, imageFile);
    }

    @Override
    public List<DeliveryPersonDto> getAllDeliveryPersonsByVille(String ville) {
        return deliveryManService.getAllDeliveryPersonsByVille(ville);
    }

    @Override
    public DeliveryPersonDto findById(Integer id) {
        return deliveryManService.findById(id);
    }

    @Override
    public List<DeliveryPersonDto> findAll() {
        return deliveryManService.findAll();
    }

    @Override
    public void delete(Integer id) {
        deliveryManService.delete(id);
    }
}
