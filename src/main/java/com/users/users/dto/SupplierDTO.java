package com.users.users.dto;

import lombok.*;

@Setter
@Getter
public class SupplierDTO {
    // Getters and setters
    private String name;
    private String contactDetails;
    private String imageFileName;


    public SupplierDTO() {}

    public SupplierDTO(String name, String contactDetails, String imageFileName) {
        this.name = name;
        this.contactDetails = contactDetails;
        this.imageFileName =imageFileName;

    }
    public SupplierDTO(String name, String contactDetails) {
        this.name = name;
        this.contactDetails = contactDetails;

    }

}