package gestion.commandeProduit.service;


import gestion.commandeProduit.entities.CommandeCompaniesFinal;
import gestion.commandeProduit.entities.CommandeIndividualClientFinal;

import java.util.List;
import java.util.Optional;

public interface CommandeIndividualClientFinalService {


    CommandeIndividualClientFinal createCommande(CommandeIndividualClientFinal commande);

    List<CommandeIndividualClientFinal> getAllCommandes();

    Optional<CommandeIndividualClientFinal> getCommandeById(Integer id);

    CommandeIndividualClientFinal updateCommande(Integer id, CommandeIndividualClientFinal commande);

    void deleteCommandeById(Integer id);
}
