package com.users.users.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users.controler.api.CompaniesApi;
import com.users.users.controler.api.GasRetailerApi;
import com.users.users.dto.CompaniesDto;
import com.users.users.dto.GasRetailerDto;
import com.users.users.services.interfaces.CompaniesService;
import com.users.users.services.interfaces.GasRetailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class GasRetailerControllerImpl implements GasRetailerApi {

    private final GasRetailerService gasRetailerService;
    private final ObjectMapper objectMapper;


    @Autowired
    public GasRetailerControllerImpl( GasRetailerService gasRetailerService, ObjectMapper objectMapper) {
        this.gasRetailerService = gasRetailerService;
        this.objectMapper = objectMapper;
//        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public GasRetailerDto save(@RequestPart("GasRetailerDto") String gasRetailerJson, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {


        GasRetailerDto gasRetailerDto = objectMapper.readValue(gasRetailerJson, GasRetailerDto.class);
        return gasRetailerService.save(gasRetailerDto, imageFile);
    }

    @Override
    public GasRetailerDto findById(Integer id) {
        return gasRetailerService.findById(id);
    }

    @Override
    public List<GasRetailerDto> findAll() {
        return gasRetailerService.findAll();
    }

    @Override
    public void delete(Integer id) {
        gasRetailerService.delete(id);
    }
}
