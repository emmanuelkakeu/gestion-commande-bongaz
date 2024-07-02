package com.users.users.controler.api;

import com.users.users.dto.IndividualClientDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.APP_ROOT;
import static com.users.users.utils.Constants.INDIVIDUALCLIENT_ENDPOINT;
@RequestMapping("/gestionUtilisateurs/v1")
//@Api("IndividualClientApi")
@Tag(name = "IndividualClient Management", description = "Endpoint to manage IndividualClient")
public interface IndividualClientApi {

    @PostMapping(value = INDIVIDUALCLIENT_ENDPOINT +"/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto save(@RequestPart("individualClientDto") String individualClientJson,
                             @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value =  INDIVIDUALCLIENT_ENDPOINT +  "/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    IndividualClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value =  INDIVIDUALCLIENT_ENDPOINT +  "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<IndividualClientDto> findAll();

    @DeleteMapping(value = INDIVIDUALCLIENT_ENDPOINT +   "/delete/{idIndividualClient}")
    void delete(@PathVariable("idIndividualClient") Integer id);

}
