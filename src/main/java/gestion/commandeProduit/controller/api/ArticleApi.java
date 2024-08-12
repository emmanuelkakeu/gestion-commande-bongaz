package gestion.commandeProduit.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;

import gestion.commandeProduit.entities.ArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static gestion.commandeProduit.utils.Constants.*;


@Tag(name = "Article Management", description = "Endpoint to manage articles")
public interface ArticleApi {

    @PostMapping(value = ENDPOINT_ARTICLE + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ArticleDto> save(@RequestPart("articleDto") String articleJson,
                                    @RequestPart("imageFile") MultipartFile imageFile,
                                    @RequestPart("imageFiles") MultipartFile[] imageFiles) throws IOException;

    @GetMapping(value = ENDPOINT_ARTICLE + "/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ArticleDto> findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value = ENDPOINT_ARTICLE + "/gasRetailerId/{gasRetailerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ArticleDto>> findByGasRetailerId(@PathVariable("gasRetailerId") Integer gasRetailerId);

    @GetMapping(value = ENDPOINT_ARTICLE + "/supplierId/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ArticleDto>> findBySupplierId(@PathVariable("supplierId") Integer supplierId);

    @GetMapping(value = ENDPOINT_ARTICLE + "/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ArticleDto> findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(value = ENDPOINT_ARTICLE + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ArticleDto>> findAll();

    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneVenteDto>> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LigneCommandeSupplierDto>> findHistoriqueCommandeSupplier(@PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(value = ENDPOINT_ARTICLE + "/delete/{idArticle}")
    ResponseEntity<Void> delete(@PathVariable("idArticle") Integer id);

    @Operation(summary = "Search for articles", description = "Search articles based on query, page number, and page size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the articles",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ArticleResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Articles not found",
                    content = @Content) })
    @GetMapping(value = ENDPOINT_ARTICLE + "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> searchArticles(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Page number") @RequestParam int page,
            @Parameter(description = "Page size") @RequestParam int size);
}
