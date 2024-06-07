package gestion.commandeProduit.controller;

import java.util.List;

import gestion.commandeProduit.DTO.IndividualClientDto;
import gestion.commandeProduit.controller.api.IndividualClientApi;
import gestion.commandeProduit.service.IndividualClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndividualClientController implements IndividualClientApi {

    private final IndividualClientService individualClientService;

    @Autowired
    public IndividualClientController(IndividualClientService individualClientService) {
        this.individualClientService = individualClientService;
    }

    @Override
    public IndividualClientDto save(IndividualClientDto dto) {
        return individualClientService.save(dto);
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
