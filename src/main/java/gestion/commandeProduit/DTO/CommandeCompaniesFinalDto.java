package gestion.commandeProduit.DTO;

import gestion.commandeProduit.entities.AdresseLivraison;
import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import gestion.commandeProduit.entities.InfosCompaniesClient;
import gestion.commandeProduit.entities.enums.StatusCommandeFinal;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CommandeCompaniesFinalDto {

    private Integer id;

    private CommandeCompaniesDto commandeCompaniesDto;

    private  InfosCompaniesClient infosCompaniesClient;

    private AdresseLivraison adresseLivraison;

    private BigDecimal coutLivraison;

    private  BigDecimal coutFinal;

    private StatusCommandeFinal statusCommandeFinal;



    public static CommandeCompaniesFinalDto fromEntity(CommandeCompaniesFinal commandeCompaniesFinal){

        if(commandeCompaniesFinal == null){
            return null;
        }

        return CommandeCompaniesFinalDto.builder()
                .id(commandeCompaniesFinal.getId())
                .commandeCompaniesDto(CommandeCompaniesDto.fromEntities(commandeCompaniesFinal.getCommandeCompanies()))
                .adresseLivraison(commandeCompaniesFinal.getAdresseLivraison())
                .infosCompaniesClient(commandeCompaniesFinal.getInfosCompaniesClient())
                .coutLivraison(commandeCompaniesFinal.getCoutLivraison())
                .coutFinal(commandeCompaniesFinal.getCoutFinal())
                .statusCommandeFinal(commandeCompaniesFinal.getStatusCommandeFinal())
                .build();

    }

    public static CommandeCompaniesFinal toEntity(CommandeCompaniesFinalDto dto){

        if (dto == null){
            return null;
        }
        CommandeCompaniesFinal commandeCompaniesFinal = new CommandeCompaniesFinal();
        commandeCompaniesFinal.setCommandeCompanies(CommandeCompaniesDto.toEntities(dto.getCommandeCompaniesDto()));
        commandeCompaniesFinal.setInfosCompaniesClient(dto.getInfosCompaniesClient());
        commandeCompaniesFinal.setAdresseLivraison(dto.adresseLivraison);
        commandeCompaniesFinal.setStatusCommandeFinal(dto.statusCommandeFinal);
        commandeCompaniesFinal.setCoutLivraison(dto.getCoutLivraison());
        commandeCompaniesFinal.setCoutFinal(dto.getCoutFinal());

        return commandeCompaniesFinal;

    }
}