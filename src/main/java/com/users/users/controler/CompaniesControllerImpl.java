package com.users.users.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users.controler.api.CompaniesApi;
import com.users.users.controler.api.IndividualClientApi;
import com.users.users.dto.CompaniesDto;
import com.users.users.dto.IndividualClientDto;
import com.users.users.services.interfaces.CompaniesService;
import com.users.users.services.interfaces.IndividualClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CompaniesControllerImpl implements CompaniesApi {

    private final CompaniesService companiesService;
    private final ObjectMapper objectMapper;


    @Autowired
    public CompaniesControllerImpl(CompaniesService companiesService, ObjectMapper objectMapper) {
        this.companiesService = companiesService;
        this.objectMapper = objectMapper;
//        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public CompaniesDto save(@RequestPart("CompaniesDto") String companiesJson, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {


        CompaniesDto companiesDto = objectMapper.readValue(companiesJson, CompaniesDto.class);
        return companiesService.save(companiesDto, imageFile);
    }

    @Override
    public CompaniesDto findById(Integer id) {
        return companiesService.findById(id);
    }

    @Override
    public List<CompaniesDto> findAll() {
        return companiesService.findAll();
    }

    @Override
    public void delete(Integer id) {
        companiesService.delete(id);
    }
}