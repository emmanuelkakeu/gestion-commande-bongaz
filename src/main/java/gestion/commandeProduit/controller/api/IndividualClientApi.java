package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.IndividualClientDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.INDIVIDUALCLIENT_ENDPOINT;

//@Api("individualClient")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "individualClient Management", description = "Endpoint to manage individualClient")


@RequestMapping(INDIVIDUALCLIENT_ENDPOINT)
public interface IndividualClientApi {

    @PostMapping(value =  "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto save(@RequestBody IndividualClientDto dto);

    @GetMapping(value =  "/{idindividualClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto findById(@PathVariable("idindividualClient") Integer id);

    @GetMapping(value =  "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<IndividualClientDto> findAll();

    @DeleteMapping(value =  "/delete/{ididindividualClient}")
    void delete(@PathVariable("idindividualClient") Integer id);

}
