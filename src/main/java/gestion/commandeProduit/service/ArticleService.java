package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);


    void delete(Integer id);

}
