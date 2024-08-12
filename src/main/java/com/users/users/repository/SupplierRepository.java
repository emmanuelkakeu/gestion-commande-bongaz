package com.users.users.repository;


import com.users.users.models.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Administrateur, Integer> {
}
