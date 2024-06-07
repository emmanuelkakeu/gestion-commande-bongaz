package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.VentesDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.VENTES_ENDPOINT;

//@Api("ventes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "vente Management", description = "Endpoint to manage vente")


@RequestMapping(VENTES_ENDPOINT)
public interface VentesApi {

    @PostMapping( "/create")
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping( "/{idVente}")
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping( "/code/{codeVente}")
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping( "/all")
    List<VentesDto> findAll();

    @DeleteMapping("/delete/{idVente}")
    void delete(@PathVariable("idVente") Integer id);

}
