package gestion.commandeProduit.DTO;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<LigneCommandeIndividualClientDto> ligneCommandeIndividualClientDto;

    private String prixTotalCmmd;


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
                .prixTotalCmmd(commandeIndividualClient.getPrixTotalCmmd())
                .ligneCommandeIndividualClientDto(
                        commandeIndividualClient.getLigneCommandeIndividualClients() != null ?
                                commandeIndividualClient.getLigneCommandeIndividualClients().stream()
                                        .map(LigneCommandeIndividualClientDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
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
        commandeClient.setPrixTotalCmmd(dto.getPrixTotalCmmd());
        commandeClient.setLigneCommandeIndividualClients(
                dto.getLigneCommandeIndividualClientDto() != null ?
                        dto.getLigneCommandeIndividualClientDto().stream()
                                .map(LigneCommandeIndividualClientDto::toEntity)
                                .collect(Collectors.toList()) : null
        );
        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
