package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.VentesDto;

import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto dto);

    VentesDto findById(Integer id);

    VentesDto findByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer id);

}
