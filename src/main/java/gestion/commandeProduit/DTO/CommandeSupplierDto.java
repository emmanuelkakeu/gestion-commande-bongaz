package gestion.commandeProduit.DTO;



import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.enums.EtatCommande;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeSupplierDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private int idSupplier;

    private List<LigneCommandeSupplierDto> ligneCommandeSupplierDto;

    private String prixTotalCmmd;


    public static CommandeSupplierDto fromEntity(CommandeSupplier commandeSupplier) {
        if (commandeSupplier == null) {
            return null;
        }
        return CommandeSupplierDto.builder()
                .id(commandeSupplier.getId())
                .code(commandeSupplier.getCode())
                .dateCommande(commandeSupplier.getDateCommande())
                .idSupplier(commandeSupplier.getIdSupplier())
                .etatCommande(commandeSupplier.getEtatCommande())
                .prixTotalCmmd(commandeSupplier.getPrixTotalCmmd())
                .ligneCommandeSupplierDto(
                        commandeSupplier.getLigneCommandeSupplier() != null ?
                                commandeSupplier.getLigneCommandeSupplier().stream()
                                        .map(LigneCommandeSupplierDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static CommandeSupplier toEntity(CommandeSupplierDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeSupplier commandeSupplier = new CommandeSupplier();
        commandeSupplier.setId(dto.getId());
        commandeSupplier.setCode(dto.getCode());
        commandeSupplier.setDateCommande(dto.getDateCommande());
        commandeSupplier.setIdSupplier(dto.getIdSupplier());
        commandeSupplier.setEtatCommande(dto.getEtatCommande());
        commandeSupplier.setPrixTotalCmmd(dto.getPrixTotalCmmd());
        commandeSupplier.setLigneCommandeSupplier(
                dto.getLigneCommandeSupplierDto() != null ?
                        dto.getLigneCommandeSupplierDto().stream()
                                .map(LigneCommandeSupplierDto::toEntity)
                                .collect(Collectors.toList()) : null
        );

        return commandeSupplier;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
