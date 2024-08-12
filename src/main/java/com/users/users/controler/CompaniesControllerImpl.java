package com.users.users.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.users.controler.api.CompaniesApi;
import com.users.users.dto.CompaniesDto;
import com.users.users.services.interfaces.CompaniesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.COMPANIES_ENDPOINT;

@Slf4j
@RestController
public class CompaniesControllerImpl implements CompaniesApi {

    private final CompaniesService companiesService;
    private final ObjectMapper objectMapper;


    @Autowired
    public CompaniesControllerImpl(CompaniesService companiesService, ObjectMapper objectMapper) {
        this.companiesService = companiesService;
        this.objectMapper = objectMapper;

    }

    @Override
    @PostMapping(value = COMPANIES_ENDPOINT + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompaniesDto save(@RequestPart("companiesDto") String companiesJson, @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        CompaniesDto companiesDto = objectMapper.readValue(companiesJson, CompaniesDto.class);
        return companiesService.save(companiesDto, imageFile);
    }

    @Override
    public CompaniesDto findById(Integer id) {
        return companiesService.findById(id);
    }

    @Override
    public List<CompaniesDto> findAll(Authentication authentication) {

        return companiesService.findAll();
    }

    @Override
    public void delete(Integer id) {
        companiesService.delete(id);
    }
}