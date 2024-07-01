package gestion.commandeProduit.repository;


import aj.org.objectweb.asm.commons.Remapper;
import gestion.commandeProduit.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
     Optional<Article> findArticleByCodeArticle(String codeArticle);

    List<Article> findBySupplierId(Integer supplierId);
    List<Article> findByGasRetailerId(Integer gasRetailerId);
}

