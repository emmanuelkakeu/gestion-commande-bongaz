package gestion.commandeProduit.utils;

public interface Constants {

    String APP_ROOT = "gestionCommande/v1";

    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandesfournisseurs";

    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/filter/{codeCommandeFournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeSupplier}";

    String COMMANDE_GASRETAILER_ENDPOINT = APP_ROOT + "/commandesGasRetailer";

    String CREATE_COMMANDE_GASRETAILER_ENDPOINT = COMMANDE_GASRETAILER_ENDPOINT + "/create";
    String FIND_COMMANDE_GASRETAILER_BY_ID_ENDPOINT = COMMANDE_GASRETAILER_ENDPOINT + "/{idCommandeGasRetailer}";
    String FIND_COMMANDE_GASRETAILER_BY_CODE_ENDPOINT = COMMANDE_GASRETAILER_ENDPOINT + "/filter/{codeCommandeGasRetailer}";
    String FIND_ALL_COMMANDE_GASRETAILER_ENDPOINT = COMMANDE_GASRETAILER_ENDPOINT + "/all";
    String DELETE_COMMANDE_GASRETAILER_ENDPOINT = COMMANDE_GASRETAILER_ENDPOINT + "/delete/{idCommandeGasRetailer}";


    String COMMANDE_COMPANIES_ENDPOINT = APP_ROOT + "/commandesCompanies";

    String CREATE_COMMANDE_COMPANIES_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/create";
    String FIND_COMMANDE_COMPANIES_BY_ID_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/{idCommandeCompanies}";
    String FIND_COMMANDE_COMPANIES_BY_CODE_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/filter/{codeCommandeGasRetailer}";
    String FIND_ALL_COMMANDE_COMPANIES_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/all";
    String DELETE_COMMANDE_COMPANIES_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/delete/{idCommandeCompanies}";
    String FIND_COMMANDE_COMPANIES_BY_ETAT_ENDPOINT = COMMANDE_COMPANIES_ENDPOINT + "/etat/{etat}" ;

    String ENDPOINT_ARTICLE = APP_ROOT + "/article";


    String ENDPOINT_MVTSTK = APP_ROOT + "/mvtstk";

    String COMPANIES_ENDPOINT = APP_ROOT + "/companies";

    String SUPPLIERS_ENDPOINT = APP_ROOT + "/suppliers";

    String INDIVIDUALCLIENT_ENDPOINT = APP_ROOT + "/individualClient";

    String VENTES_ENDPOINT = APP_ROOT + "/ventes";

    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";

}
