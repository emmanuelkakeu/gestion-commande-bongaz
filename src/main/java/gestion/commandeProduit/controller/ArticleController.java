package gestion.commandeProduit.controller;

import java.util.List;

import gestion.commandeProduit.DTO.ArticleDto;
import gestion.commandeProduit.DTO.LigneCommandeIndividualClientDto;
import gestion.commandeProduit.DTO.LigneCommandeSupplierDto;
import gestion.commandeProduit.DTO.LigneVenteDto;
import gestion.commandeProduit.service.ArticleService;
import gestion.commandeProduit.service.CommandeSupplierService;
import gestion.commandeProduit.service.IndividualClientService;
import gestion.commandeProduit.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import gestion.commandeProduit.controller.api.ArticleApi;

@RestController
public class ArticleController implements ArticleApi {

    private final ArticleService articleService;
    private final IndividualClientService individualClientService;
    private final CommandeSupplierService commandeSupplierService;

    @Autowired
    public ArticleController(
            ArticleService articleService, IndividualClientService individualClientService, CommandeSupplierService commandeSupplierService
    ) {
        this.articleService = articleService;
        this.individualClientService = individualClientService;
        this.commandeSupplierService = commandeSupplierService;
    }

    @Override
    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
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

    @Override
    public List<LigneCommandeIndividualClientDto> findHistoriaueCommandeIndividualClient(Integer idArticle) {
        return individualClientService.findHistoriqueCommandeIndividualClient(idArticle);
    }

    @Override
    public List<LigneCommandeSupplierDto> findHistoriqueCommandeSupplier(Integer idArticle) {
        return commandeSupplierService.findHistoriqueCommandeSupplier(idArticle);
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
