package gestion.commandeProduit.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import gestion.commandeProduit.service.ArticleService;
import gestion.commandeProduit.service.CommandeSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import gestion.commandeProduit.controller.api.ArticleApi;
import org.springframework.web.multipart.MultipartFile;

import static gestion.commandeProduit.utils.Constants.ENDPOINT_ARTICLE;

@RestController
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;
    private final CommandeSupplierService commandeSupplierService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ArticleController(
            ArticleService articleService, CommandeSupplierService commandeSupplierService, ObjectMapper objectMapper
    ) {
        this.articleService = articleService;
        this.commandeSupplierService = commandeSupplierService;
        this.objectMapper = objectMapper;
    }

    @Override
    @PostMapping(value = ENDPOINT_ARTICLE + "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleDto save(@RequestPart("articleDto") String articleJson,
                           @RequestPart("imageFile") MultipartFile imageFile,
                           @RequestPart("imageFiles") MultipartFile[] imageFiles) throws IOException {
        ArticleDto articleDto = objectMapper.readValue(articleJson, ArticleDto.class);
        return articleService.save(articleDto, imageFile, imageFiles);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public List<ArticleDto> findByGasRetailerId(Integer gasRetailerId) {
        return articleService.findByGasRetailerId(gasRetailerId);
    }

    @Override
    public List<ArticleDto> findBySupplierId(Integer supplierId) {
        return articleService.findBySupplierId(supplierId);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return articleService.findHistoriqueVentes(idArticle);
    }

//    @Override
//    public List<LigneCommandeIndividualClientDto> findHistoriaueCommandeIndividualClient(Integer idArticle) {
//        return individualClientService.findHistoriqueCommandeIndividualClient(idArticle);
//    }

    @Override
    public List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(Integer idArticle) {
        return commandeSupplierService.findHistoriqueCommandeSupplier(idArticle);
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
