package gestion.commandeProduit.service.impl;

import gestion.commandeProduit.entities.CommandeIndividualClientFinal;
import gestion.commandeProduit.exception.ErrorCodes;
import gestion.commandeProduit.exception.InvalidEntityException;
import gestion.commandeProduit.repository.CommandeIndividualClientFinalRepository;
import gestion.commandeProduit.service.CommandeIndividualClientFinalService;
import gestion.commandeProduit.validator.AdresseLivraisonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommandeIndividualClientFinalServiceImpl implements CommandeIndividualClientFinalService {


        private final CommandeIndividualClientFinalRepository commandeIndividualClientFinalRepository;
        @Autowired
        public CommandeIndividualClientFinalServiceImpl(CommandeIndividualClientFinalRepository commandeIndividualClientFinalRepository) {

            this.commandeIndividualClientFinalRepository = commandeIndividualClientFinalRepository;
        }

        @Override
        public CommandeIndividualClientFinal createCommande(CommandeIndividualClientFinal commande) {

            List<String> errors = AdresseLivraisonValidator.validate(commande.getAdresseLivraison());
            if (!errors.isEmpty()) {
                throw new InvalidEntityException("L'adresse de livraison du client n'est pas valide", ErrorCodes.ADRESSE_LIVRAISON_CLIENT_NOT_VALID, errors);
            }
            validateCommande(commande);
            return commandeIndividualClientFinalRepository.save(commande);
        }

        @Override
        public List<CommandeIndividualClientFinal> getAllCommandes() {
            return commandeIndividualClientFinalRepository.findAll();
        }

        @Override
        public Optional<CommandeIndividualClientFinal> getCommandeById(Integer id) {
            return commandeIndividualClientFinalRepository.findById(id);
        }

        @Override
        public CommandeIndividualClientFinal updateCommande(Integer id, CommandeIndividualClientFinal commande) {
            Optional<CommandeIndividualClientFinal> existingCommandeOpt = commandeIndividualClientFinalRepository.findById(id);
            if (existingCommandeOpt.isEmpty()) {
                throw new IllegalArgumentException("CommandeCompanies with ID " + id + " not found");
            }
            CommandeIndividualClientFinal existingCommande = existingCommandeOpt.get();
            updateCommandeDetails(existingCommande, commande);
            return commandeIndividualClientFinalRepository.save(existingCommande);
        }

        @Override
        public void deleteCommandeById(Integer id) {
            Optional<CommandeIndividualClientFinal> existingCommandeOpt = commandeIndividualClientFinalRepository.findById(id);
            if (existingCommandeOpt.isEmpty()) {
                throw new IllegalArgumentException("CommandeCompanies with ID " + id + " not found");
            }
            commandeIndividualClientFinalRepository.deleteById(id);
        }

        private void validateCommande(CommandeIndividualClientFinal commande) {
            // Add your validation logic here
            if (commande.getCommandeIndividualClient() == null) {
                throw new IllegalArgumentException("CommandeCompanies cannot be null");
            }
            // Add more validations as needed
        }

        private void updateCommandeDetails(CommandeIndividualClientFinal existingCommande, CommandeIndividualClientFinal newDetails) {
            existingCommande.setCommandeIndividualClient(newDetails.getCommandeIndividualClient());
            existingCommande.setInfosClientIndividual(newDetails.getInfosClientIndividual());
            existingCommande.setAdresseLivraison(newDetails.getAdresseLivraison());
            existingCommande.setCoutLivraison(newDetails.getCoutLivraison());
            existingCommande.setCoutFinal(newDetails.getCoutFinal());
            existingCommande.setStatusCommandeFinal(newDetails.getStatusCommandeFinal());
        }



}
