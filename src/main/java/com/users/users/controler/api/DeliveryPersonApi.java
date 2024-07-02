package com.users.users.controler.api;

import com.users.users.dto.DeliveryPersonDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.APP_ROOT;
import static com.users.users.utils.Constants.DELIVERYPERSON_ENDPOINT;
@RequestMapping("/gestionUtilisateurs/v1")
//@Api("DeliveryPersonApi")
@Tag(name = "Delivery Person Management", description = "Endpoint to manage Delivery Personr")
public interface DeliveryPersonApi {

    @PostMapping(value = DELIVERYPERSON_ENDPOINT + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    DeliveryPersonDto save(@RequestPart("DeliveryPersonDto") String deliveryPersonJson,
                           @RequestPart("imageFile") MultipartFile imageFile) throws IOException;

    @GetMapping(value = DELIVERYPERSON_ENDPOINT + "/byVille/{ville}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<DeliveryPersonDto> getAllDeliveryPersonsByVille(String ville);

    @GetMapping(value = DELIVERYPERSON_ENDPOINT + "/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    DeliveryPersonDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = DELIVERYPERSON_ENDPOINT + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<DeliveryPersonDto> findAll();

    @DeleteMapping(value =DELIVERYPERSON_ENDPOINT + "/delete/{idDeliveryPersonDto}")
    void delete(@PathVariable("idDeliveryPersonDto") Integer id);

}
