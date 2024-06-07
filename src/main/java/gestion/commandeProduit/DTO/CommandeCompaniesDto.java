package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.CommandeCompanies;
import gestion.commandeProduit.entities.LigneCommandeCompanies;
import gestion.commandeProduit.entities.enums.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommandeCompaniesDto {

    private int id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private CompaniesDto companiesDto;

    private List<LigneCommandeCompaniesDto> ligneCommandeCompaniesDto;

    public  static CommandeCompaniesDto fromEntities(CommandeCompanies commandeCompanies){

        if (commandeCompanies == null){
            return  null;
        }

        return CommandeCompaniesDto.builder()
                .id(commandeCompanies.getId())
                .code(commandeCompanies.getCode())
                .dateCommande(commandeCompanies.getDateCommande())
                .etatCommande(commandeCompanies.getEtatCommande())
                .companiesDto(CompaniesDto.fromEntity(commandeCompanies.getCompanies()))
                .build();
    }


    public static CommandeCompanies toEntities(CommandeCompaniesDto dto){

        if (dto == null){
            return  null;
        }

        CommandeCompanies commandeCompanies = new CommandeCompanies();
        commandeCompanies.setId(dto.getId());
        commandeCompanies.setCode(dto.getCode());
        commandeCompanies.setDateCommande(dto.getDateCommande());
        commandeCompanies.setEtatCommande(dto.getEtatCommande());
        commandeCompanies.setCompanies(CompaniesDto.toEntity(dto.getCompaniesDto()));

        return commandeCompanies;
    }
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
