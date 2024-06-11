package com.users.users.controler;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.users.users.controler.api.IndividualClientApi;
import com.users.users.dto.IndividualClientDto;
import com.users.users.dto.SupplierDto;
import com.users.users.services.interfaces.IndividualClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IndividualClientController implements IndividualClientApi {

    private final IndividualClientService individualClientService;
    private final ObjectMapper objectMapper;


    @Autowired
    public IndividualClientController(IndividualClientService individualClientService, ObjectMapper objectMapper) {
        this.individualClientService = individualClientService;
        this.objectMapper = objectMapper;
//        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public IndividualClientDto save(@RequestPart("individualClientDto") String individualClientJson, @RequestPart("imageFile") MultipartFile imageFile) throws IOException  {


        IndividualClientDto individualClientDto = objectMapper.readValue(individualClientJson, IndividualClientDto.class);
        return individualClientService.save(individualClientDto, imageFile);
    }

    @Override
    public IndividualClientDto findById(Integer id) {
        return individualClientService.findById(id);
    }

    @Override
    public List<IndividualClientDto> findAll() {
        return individualClientService.findAll();
    }

    @Override
    public void delete(Integer id) {
        individualClientService.delete(id);
    }
}