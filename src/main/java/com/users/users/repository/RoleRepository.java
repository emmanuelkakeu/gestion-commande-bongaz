package com.users.users.repository;

import com.users.users.models.Role;
import com.users.users.models.enums.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByLibelle(TypeRole libelle);
}
