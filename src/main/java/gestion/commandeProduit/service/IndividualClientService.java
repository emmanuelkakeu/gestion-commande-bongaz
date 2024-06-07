package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.IndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.entities.IndividualClient;
import gestion.commandeProduit.entities.LigneCommandeIndividualClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IndividualClientService {


    IndividualClientDto save(IndividualClientDto dto);

    IndividualClientDto findById(Integer id);

    List<IndividualClientDto> findAll();

    void delete(Integer id);

    List<LigneCommandeIndividualClientDto> findHistoriqueCommandeIndividualClient(Integer idArticle);

}
