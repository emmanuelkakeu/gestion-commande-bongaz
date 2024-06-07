package gestion.commandeProduit.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import gestion.commandeProduit.entities.CommandeIndividualClient;
import gestion.commandeProduit.entities.IndividualClient;
import gestion.commandeProduit.entities.enums.EtatCommande;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeIndividualClientDto {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private IndividualClientDto individualClient;


    private List<LigneCommandeIndividualClientDto> ligneCommandeClients;

    public static CommandeIndividualClientDto fromEntity(CommandeIndividualClient commandeIndividualClient) {
        if (commandeIndividualClient == null) {
            return null;
        }
        return CommandeIndividualClientDto.builder()
                .id(commandeIndividualClient.getId())
                .code(commandeIndividualClient.getCode())
                .dateCommande(commandeIndividualClient.getDateCommande())
                .etatCommande(commandeIndividualClient.getEtatCommande())
                .individualClient(IndividualClientDto.fromEntity(commandeIndividualClient.getIndividualClient()))
                .build();

    }

    public static CommandeIndividualClient toEntity(CommandeIndividualClientDto dto) {
        if (dto == null) {
            return null;
        }
        CommandeIndividualClient commandeClient = new CommandeIndividualClient();
        commandeClient.setId(dto.getId());
        commandeClient.setCode(dto.getCode());
        commandeClient.setIndividualClient(IndividualClientDto.toEntity(dto.getIndividualClient()));
        commandeClient.setDateCommande(dto.getDateCommande());
        commandeClient.setEtatCommande(dto.getEtatCommande());

        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
