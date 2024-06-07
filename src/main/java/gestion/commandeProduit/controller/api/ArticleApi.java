package gestion.commandeProduit.controller.api;

import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gestion.commandeProduit.utils.Constants.APP_ROOT;

//@Api("articles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Article Management", description = "Endpoint to manage article")

@RequestMapping(APP_ROOT)
public interface ArticleApi {

    @PostMapping(value =  "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Enregistrer un article", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'objet article cree / modifie"),
//            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
//    })
    ArticleDto save(@RequestBody ArticleDto dto);


    @GetMapping(value =   "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
//            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
//    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);


    @GetMapping(value =  "/articles/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
//            ArticleDto.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
//            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
//    })
    ArticleDto findByCodeArticle(@PathVariable("codeArticle") String codeArticle);


    @GetMapping(value = "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
//            + "dans la BDD", responseContainer = "List<ArticleDto>")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
//    })
    List<ArticleDto> findAll();


    @GetMapping(value =  "/articles/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneVenteDto> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);


    @GetMapping(value =  "/articles/historique/commandeclient/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeIndividualClientDto> findHistoriaueCommandeIndividualClient(@PathVariable("idArticle") Integer idArticle);


    @GetMapping(value =  "/articles/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(@PathVariable("idArticle") Integer idArticle);


    @DeleteMapping(value =  "/articles/delete/{idArticle}")
//    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "L'article a ete supprime")
//    })
    void delete(@PathVariable("idArticle") Integer id);

}
