package gestion.commandeProduit.DTO;



import java.time.Instant;
import java.util.List;

import gestion.commandeProduit.entities.CommandeSupplier;
import gestion.commandeProduit.entities.enums.EtatCommande;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeSupplierDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private SupplierDto supplier;


    private List<LigneCommandeSupplierDto> ligneCommandeSupplier;

    public static CommandeSupplierDto fromEntity(CommandeSupplier commandeSupplier) {
        if (commandeSupplier == null) {
            return null;
        }
        return CommandeSupplierDto.builder()
                .id(commandeSupplier.getId())
                .code(commandeSupplier.getCode())
                .dateCommande(commandeSupplier.getDateCommande())
                .supplier(SupplierDto.fromEntity(commandeSupplier.getSupplier()))
                .etatCommande(commandeSupplier.getEtatCommande())
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
        commandeSupplier.setSupplier(SupplierDto.toEntity(dto.getSupplier()));
        commandeSupplier.setEtatCommande(dto.getEtatCommande());
        return commandeSupplier;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }

}
