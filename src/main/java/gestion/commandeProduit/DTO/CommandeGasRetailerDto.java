package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.CommandeGasRetailer;
import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.LigneCommandeGasRetailer;
import gestion.commandeProduit.entities.enums.EtatCommande;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CommandeGasRetailerDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private int idGasRetailer;

    private List<LigneCommandeGasRetailerDto> ligneCommandeGasRetailersDto;

    private String prixTotalCmmd;

    public static CommandeGasRetailerDto fromEntity(CommandeGasRetailer commandeGasRetailer) {
        if (commandeGasRetailer == null) {
            return null;
        }
        return CommandeGasRetailerDto.builder()
                .id(commandeGasRetailer.getId())
                .code(commandeGasRetailer.getCode())
                .dateCommande(commandeGasRetailer.getDateCommande())
                .idGasRetailer(commandeGasRetailer.getIdGasRetailer())
                .etatCommande(commandeGasRetailer.getEtatCommande())
                .prixTotalCmmd(commandeGasRetailer.getPrixTotalCmmd())
                .ligneCommandeGasRetailersDto(
                        commandeGasRetailer.getLigneCommandeGasRetailer() != null ?
                                commandeGasRetailer.getLigneCommandeGasRetailer().stream()
                                        .map(LigneCommandeGasRetailerDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static CommandeGasRetailer toEntity(CommandeGasRetailerDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeGasRetailer  commandeGasRetailer = new CommandeGasRetailer();
        commandeGasRetailer.setId(dto.getId());
        commandeGasRetailer.setCode(dto.getCode());
        commandeGasRetailer.setDateCommande(dto.getDateCommande());
        commandeGasRetailer.setIdGasRetailer(dto.getIdGasRetailer());
        commandeGasRetailer.setEtatCommande(dto.getEtatCommande());
        commandeGasRetailer.setPrixTotalCmmd(dto.getPrixTotalCmmd());
        commandeGasRetailer.setLigneCommandeGasRetailer(
                dto.getLigneCommandeGasRetailersDto() != null ?
                        dto.getLigneCommandeGasRetailersDto().stream()
                                .map(LigneCommandeGasRetailerDto::toEntity)
                                .collect(Collectors.toList()) : null
        );

        return commandeGasRetailer;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}

