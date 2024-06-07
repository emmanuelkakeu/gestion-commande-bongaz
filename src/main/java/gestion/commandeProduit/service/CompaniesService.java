package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.CompaniesDto;
import gestion.commandeProduit.DTO.IndividualClientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompaniesService {

    CompaniesDto save(CompaniesDto dto);

    CompaniesDto findById(Integer id);

    List<CompaniesDto> findAll();

    void delete(Integer id);

}
