package gestion.commandeProduit.repository;


import aj.org.objectweb.asm.commons.Remapper;
import gestion.commandeProduit.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
     Optional<Article> findArticleByCodeArticle(String codeArticle);

    List<Article> findBySupplierId(Integer supplierId);
    List<Article> findByGasRetailerId(Integer gasRetailerId);

    @Query("SELECT a FROM Article a WHERE a.nameArticle LIKE %:query% OR a.designation LIKE %:query%")
    Page<Article> searchArticles(String query, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Article a WHERE a.nameArticle LIKE %:query% OR a.designation LIKE %:query%")
    long countArticles(@Param("query") String query);
}

