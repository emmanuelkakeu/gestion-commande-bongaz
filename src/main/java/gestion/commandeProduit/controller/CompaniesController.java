package gestion.commandeProduit.controller;

import java.util.List;

import gestion.commandeProduit.DTO.CompaniesDto;
import gestion.commandeProduit.DTO.IndividualClientDto;
import gestion.commandeProduit.controller.api.CompaniesApi;
import gestion.commandeProduit.controller.api.IndividualClientApi;
import gestion.commandeProduit.service.CompaniesService;
import gestion.commandeProduit.service.IndividualClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompaniesController implements CompaniesApi {

    private final CompaniesService companiesService;

    @Autowired
    public CompaniesController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @Override
    public CompaniesDto save(CompaniesDto dto) {
        return companiesService.save(dto);
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

