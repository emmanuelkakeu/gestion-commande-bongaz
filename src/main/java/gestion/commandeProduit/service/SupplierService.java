package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.SupplierDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplierService {

    SupplierDto save(SupplierDto dto);

    SupplierDto findById(Integer id);

    List<SupplierDto> findAll();

    void delete(Integer id);


}
