package gestion.commandeProduit.DTO;



import java.time.Instant;
import java.util.List;

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

    private List<LigneCommandeSupplierDto> ligneCommandeSupplier;

    private String prixTolalCmmd;


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
                .prixTolalCmmd(commandeSupplier.getPrixTolalCmmd())
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
        commandeSupplier.setPrixTolalCmmd(dto.getPrixTolalCmmd());
        return commandeSupplier;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
