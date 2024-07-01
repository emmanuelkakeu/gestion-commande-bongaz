package gestion.commandeProduit.service;

import gestion.commandeProduit.DTO.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ArticleService {

    ArticleDto save(ArticleDto dto, MultipartFile imageFile, MultipartFile[] imageFiles) throws IOException;

    ArticleDto findById(Integer id);

    List<ArticleDto> findBySupplierId(Integer supplierId);

    List<ArticleDto> findByGasRetailerId(Integer gasRetailerId);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);


    void delete(Integer id);

}
