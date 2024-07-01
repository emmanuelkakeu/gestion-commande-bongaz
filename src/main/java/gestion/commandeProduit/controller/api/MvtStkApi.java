package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.MvtStkDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;
import static gestion.commandeProduit.utils.Constants.ENDPOINT_MVTSTK;



@Tag(name = "Mvkstk Management", description = "Endpoint to manage mvtstk")
public interface MvtStkApi {

    @GetMapping( ENDPOINT_MVTSTK + "/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping( ENDPOINT_MVTSTK + "/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping(ENDPOINT_MVTSTK + "/entree")
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping(ENDPOINT_MVTSTK + "/sortie")
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping(ENDPOINT_MVTSTK + "/correctionpos")
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @PostMapping(ENDPOINT_MVTSTK + "/correctionneg")
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);

}
