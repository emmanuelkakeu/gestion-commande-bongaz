package gestion.commandeProduit.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import gestion.commandeProduit.service.ArticleService;
import gestion.commandeProduit.service.CommandeSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import gestion.commandeProduit.controller.api.ArticleApi;
import org.springframework.web.multipart.MultipartFile;

import static gestion.commandeProduit.utils.Constants.ENDPOINT_ARTICLE;

@RestController
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;
    private final CommandeSupplierService commandeSupplierService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ArticleController(ArticleService articleService, CommandeSupplierService commandeSupplierService, ObjectMapper objectMapper) {
        this.articleService = articleService;
        this.commandeSupplierService = commandeSupplierService;
        this.objectMapper = objectMapper;
    }

    @Override
    @PostMapping(value = ENDPOINT_ARTICLE + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArticleDto> save(@RequestPart("articleDto") String articleJson,
                                           @RequestPart("imageFile") MultipartFile imageFile,
                                           @RequestPart("imageFiles") MultipartFile[] imageFiles) throws IOException {
        ArticleDto articleDto = objectMapper.readValue(articleJson, ArticleDto.class);
        ArticleDto savedArticle = articleService.save(articleDto, imageFile, imageFiles);
        return ResponseEntity.ok(savedArticle);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> findById(@PathVariable("idArticle") Integer id) {
        ArticleDto article = articleService.findById(id);
        return ResponseEntity.ok(article);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/gasRetailerId/{gasRetailerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDto>> findByGasRetailerId(@PathVariable("gasRetailerId") Integer gasRetailerId) {
        List<ArticleDto> articles = articleService.findByGasRetailerId(gasRetailerId);
        return ResponseEntity.ok(articles);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/supplierId/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDto>> findBySupplierId(@PathVariable("supplierId") Integer supplierId) {
        List<ArticleDto> articles = articleService.findBySupplierId(supplierId);
        return ResponseEntity.ok(articles);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDto> findByCodeArticle(@PathVariable("codeArticle") String codeArticle) {
        ArticleDto article = articleService.findByCodeArticle(codeArticle);
        return ResponseEntity.ok(article);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDto>> findAll() {
        List<ArticleDto> articles = articleService.findAll();
        return ResponseEntity.ok(articles);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/vente/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LigneVenteDto>> findHistoriqueVentes(@PathVariable("idArticle") Integer idArticle) {
        List<LigneVenteDto> ventes = articleService.findHistoriqueVentes(idArticle);
        return ResponseEntity.ok(ventes);
    }

    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/historique/commandefournisseur/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LigneCommandeSupplierDto>> findHistoriqueCommandeSupplier(@PathVariable("idArticle") Integer idArticle) {
        List<LigneCommandeSupplierDto> commandes = commandeSupplierService.findHistoriqueCommandeSupplier(idArticle);
        return ResponseEntity.ok(commandes);
    }

    @Override
    @DeleteMapping(value = ENDPOINT_ARTICLE + "/delete/{idArticle}")
    public ResponseEntity<Void> delete(@PathVariable("idArticle") Integer id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Override
    @GetMapping(value = ENDPOINT_ARTICLE + "/search")
    public ResponseEntity<Map<String, Object>> searchArticles(
            @RequestParam String query,
            @RequestParam int page,
            @RequestParam int size) {

        List<ArticleDto> articles = articleService.searchArticles(query, page, size);
        long totalItems = articleService.countArticles(query);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        Map<String, Object> response = new HashMap<>();
        response.put("data", articles);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);

        return ResponseEntity.ok(response);
    }


}
