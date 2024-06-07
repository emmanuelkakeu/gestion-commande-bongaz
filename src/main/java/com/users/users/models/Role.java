package com.users.users.models;

import com.users.users.enums.Roles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "idUsers")
    private Users users;

}
