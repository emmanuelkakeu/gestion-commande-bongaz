package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.MvtStkDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface MvtStkService {

    BigDecimal stockReelArticle(Integer idArticle);

    List<MvtStkDto> mvtStkArticle(Integer idArticle);

    MvtStkDto entreeStock(MvtStkDto dto);

    MvtStkDto sortieStock(MvtStkDto dto);

    MvtStkDto correctionStockPos(MvtStkDto dto);

    MvtStkDto correctionStockNeg(MvtStkDto dto);


}