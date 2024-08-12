package gestion.commandeProduit.entities;

import gestion.commandeProduit.DTO.ArticleDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

 public class ArticleResponse {
    @Schema(description = "List of articles")
    private List<ArticleDto> data;

    @Schema(description = "Total number of articles")
    private long totalItems;

    @Schema(description = "Total number of pages")
    private int totalPages;

    // Getters and Setters
}
