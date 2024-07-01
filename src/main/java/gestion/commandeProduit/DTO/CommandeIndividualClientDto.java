package gestion.commandeProduit.DTO;

import java.time.Instant;
import java.util.List;

import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.enums.EtatCommande;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeIndividualClientDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private int idIndividualClient;

    private List<LigneCommandeIndividualClientDto> ligneCommandeClients;

    private String prixTolalCmmd;


    public static CommandeIndividualClientDto fromEntity(CommandeIndividualClient commandeIndividualClient) {
        if (commandeIndividualClient == null) {
            return null;
        }
        return CommandeIndividualClientDto.builder()
                .id(commandeIndividualClient.getId())
                .code(commandeIndividualClient.getCode())
                .dateCommande(commandeIndividualClient.getDateCommande())
                .etatCommande(commandeIndividualClient.getEtatCommande())
                .idIndividualClient(commandeIndividualClient.getIdIndividualClient())
                .prixTolalCmmd(commandeIndividualClient.getPrixTolalCmmd())
                .build();

    }

    public static CommandeIndividualClient toEntity(CommandeIndividualClientDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeIndividualClient commandeClient = new CommandeIndividualClient();
        commandeClient.setId(dto.getId());
        commandeClient.setCode(dto.getCode());
        commandeClient.setIdIndividualClient(dto.getIdIndividualClient());
        commandeClient.setDateCommande(dto.getDateCommande());
        commandeClient.setEtatCommande(dto.getEtatCommande());
        commandeClient.setPrixTolalCmmd(dto.getPrixTolalCmmd());

        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
