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

@Data
@Builder
public class CommandeGasRetailerDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private int idGasRetailer;

    private List<LigneCommandeGasRetailer> ligneCommandeGasRetailers;

    private String prixTolalCmmd;

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
                .prixTolalCmmd(commandeGasRetailer.getPrixTolalCmmd())
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
        commandeGasRetailer.setPrixTolalCmmd(dto.getPrixTolalCmmd());
        return commandeGasRetailer;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}

