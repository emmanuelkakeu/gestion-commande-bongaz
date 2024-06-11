package com.users.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.users.users.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Users extends AbstractEntity{



    @Column(name = "firstName")
    private String firstName;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "adresse")
    private Adresse adresse;

    @Column(name = "password")
    private String password;

    @Column(name = "contactDetails")
    private String contactDetails;

    @Column(name = "status")
    private Status status;

    @Column(name = "dateCreated")
    private Date dateCreated;

    @Column(name = "imageFileName")
    private String imageFileName;

    @OneToMany
    private List<Role> roles;


}
