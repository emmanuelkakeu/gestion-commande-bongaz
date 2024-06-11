package com.users.users.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {





    CLIENT_NOT_FOUND(3000),
    CLIENT_NOT_VALID(3001),
    CLIENT_ALREADY_IN_USE(3002),

    FOURNISSEUR_NOT_FOUND(7000),
    FOURNISSEUR_NOT_VALID(7001),
    FOURNISSEUR_ALREADY_IN_USE(7002),

    COMPANIES_NOT_VALID(90000),
    COMPANIES_ALREADY_IN_USE(1500),

    UTILISATEUR_NOT_FOUND(12000),
    UTILISATEUR_NOT_VALID(12001),
    UTILISATEUR_ALREADY_EXISTS(12002),
    UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003),

    BAD_CREDENTIALS(12003),

    // Liste des exception techniaues
    UPDATE_PHOTO_EXCEPTION(14000);



    private final int code;

    ErrorCodes(int code) {
        this.code = code;
    }

}
