package com.users.users.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gasService")
public class GasService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serviceName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "gasRetailerId")
    private GasRetailer gasRetailer;

    // Getters and Setters
}