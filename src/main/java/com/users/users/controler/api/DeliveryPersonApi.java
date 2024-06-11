package com.users.users.controler.api;

import com.users.users.dto.DeliveryPersonDto;
import com.users.users.dto.IndividualClientDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.users.users.utils.Constants.*;

@RequestMapping(DELIVERYPERSON_ENDPOINT)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Delivery Person Management", description = "Endpoint to manage Delivery Personr")
public interface DeliveryPersonApi {

    @PostMapping(value = APP_ROOT + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    DeliveryPersonDto save(@RequestPart("DeliveryPersonDto") String deliveryPersonJson,
                           @RequestPart("imageFile") MultipartFile imageFile) throws IOException;


    @GetMapping(value = APP_ROOT + "/deliveryPersonDto/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    DeliveryPersonDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/deliveryPersonDto/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<DeliveryPersonDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/deliveryPersonDto/delete/{idDeliveryPersonDto}")
    void delete(@PathVariable("idDeliveryPersonDto") Integer id);

}
