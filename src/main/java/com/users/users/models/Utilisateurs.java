package com.users.users.models;

import com.users.users.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString(exclude = "role") // Exclut le rôle pour éviter la récursion infinie
public class Utilisateurs extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "name")
    private String name;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Embedded
    private Adresse adresse;

    @Column(name = "password")
    private String password;

    @Column(name = "contactDetails")
    private String contactDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreated")
    private Date dateCreated;

    @Column(name = "imageFileName")
    private String imageFileName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getLibelle()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ou une autre logique
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ou une autre logique
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // ou une autre logique
    }

    @Override
    public boolean isEnabled() {
        return true; // ou une autre logique
    }
}
