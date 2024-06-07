package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.MvtStkDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;

//@Api("mvtstk")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "mvkstk Management", description = "Endpoint to manage mvtstk")


@RequestMapping(APP_ROOT)
public interface MvtStkApi {

    @GetMapping( "/mvtstk/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping( "/mvtstk/filter/article/{idArticle}")
    List<MvtStkDto> mvtStkArticle(@PathVariable("idArticle") Integer idArticle);

    @PostMapping( "/mvtstk/entree")
    MvtStkDto entreeStock(@RequestBody MvtStkDto dto);

    @PostMapping( "/mvtstk/sortie")
    MvtStkDto sortieStock(@RequestBody MvtStkDto dto);

    @PostMapping( "/mvtstk/correctionpos")
    MvtStkDto correctionStockPos(@RequestBody MvtStkDto dto);

    @PostMapping( "/mvtstk/correctionneg")
    MvtStkDto correctionStockNeg(@RequestBody MvtStkDto dto);

}
