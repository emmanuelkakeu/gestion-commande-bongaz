package gestion.commandeProduit.controller;

import java.math.BigDecimal;
import java.util.List;

import gestion.commandeProduit.DTO.MvtStkDto;
import gestion.commandeProduit.controller.api.MvtStkApi;
import gestion.commandeProduit.service.MvtStkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MvtStkController implements MvtStkApi {

    private final MvtStkService service;

    @Autowired
    public MvtStkController(MvtStkService service) {
        this.service = service;
    }

    @Override
    public BigDecimal stockReelArticle(Integer idArticle) {
        return service.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> mvtStkArticle(Integer idArticle) {
        return service.mvtStkArticle(idArticle);
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return service.entreeStock(dto);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return service.sortieStock(dto);
    }

    @Override
    public MvtStkDto correctionStockPos(MvtStkDto dto) {
        return service.correctionStockPos(dto);
    }

    @Override
    public MvtStkDto correctionStockNeg(MvtStkDto dto) {
        return service.correctionStockNeg(dto);
    }
}
