package gestion.commandeProduit.DTO;

import java.math.BigDecimal;
import java.time.Instant;

import gestion.commandeProduit.entities.MvtStk;
import gestion.commandeProduit.entities.enums.SourceMvtStk;
import gestion.commandeProduit.entities.enums.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MvtStkDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDto articleDto;

    private TypeMvtStk typeMvt;

    private SourceMvtStk sourceMvt;


    public static MvtStkDto fromEntity(MvtStk mvtStk) {
        if (mvtStk == null) {
            return null;
        }

        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .articleDto(ArticleDto.fromEntity(mvtStk.getArticle()))
                .typeMvt(mvtStk.getTypeMvt())
                .sourceMvt(mvtStk.getSourceMvt())
//                .idEntreprise(mvtStk.getIdEntreprise())
                .build();
    }

    public static MvtStk toEntity(MvtStkDto dto) {
        if (dto == null) {
            return null;
        }

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(dto.getId());
        mvtStk.setDateMvt(dto.getDateMvt());
        mvtStk.setQuantite(dto.getQuantite());
        mvtStk.setArticle(ArticleDto.toEntity(dto.getArticleDto()));
        mvtStk.setTypeMvt(dto.getTypeMvt());
        mvtStk.setSourceMvt(dto.getSourceMvt());
//        mvtStk.setIdEntreprise(dto.getIdEntreprise());
        return mvtStk;
    }
}
