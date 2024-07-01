package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.VentesDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.VENTES_ENDPOINT;



@Tag(name = "Vente Management", description = "Endpoint to manage vente")
public interface VentesApi {

    @PostMapping(VENTES_ENDPOINT + "/create")
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping(VENTES_ENDPOINT + "/{idVente}")
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping( VENTES_ENDPOINT +"/code/{codeVente}")
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(VENTES_ENDPOINT + "/all")
    List<VentesDto> findAll();

    @DeleteMapping(VENTES_ENDPOINT +"/delete/{idVente}")
    void delete(@PathVariable("idVente") Integer id);

}
