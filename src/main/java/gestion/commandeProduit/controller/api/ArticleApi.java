package gestion.commandeProduit.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static gestion.commandeProduit.utils.Constants.*;



@Tag(name = "Article Management", description = "Endpoint to manage article")

public interface ArticleApi {

    @PostMapping(value = ENDPOINT_ARTICLE + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto save(@RequestPart("articleDto") String articleJson,
                    @RequestPart("imageFile") MultipartFile imageFile,
                    @RequestPart("imageFiles") MultipartFile[] imageFiles) throws IOException;


    @GetMapping(value =ENDPOINT_ARTICLE + "/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value =ENDPOINT_ARTICLE + "/gasRetailerId/{gasRetailerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findByGasRetailerId(@PathVariable("gasRetailerId") Integer gasRetailerId);

    @GetMapping(value =ENDPOINT_ARTICLE + "/supplierId/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findBySupplierId(@PathVariable("supplierId") Integer supplierId);


    @GetMapping(value = ENDPOINT_ARTICLE + "/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
//            ArticleDto.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
//            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
//    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);


    @GetMapping(value = ENDPOINT_ARTICLE + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
//            + "dans la BDD", responseContainer = "List<ArticleDto>")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
//    })
    List<ArticleDto> findAll();


    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);


//    @GetMapping(value =  "/articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    List<LigneCommandeIndividualClientDto> findHistoriaueCommandeIndividualClient(@PathVariable("idArticle") Integer idArticle);


    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(@PathVariable("idArticle") Integer idArticle);


    @DeleteMapping(value = ENDPOINT_ARTICLE + "/delete/{idArticle}")
//    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'article a ete supprime")
//    })
    void delete(@PathVariable("idArticle") Integer id);

}
