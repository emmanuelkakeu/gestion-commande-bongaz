package com.users.users.repository;

import com.users.users.models.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryPerson, Integer> {

    List<DeliveryPerson> findByAdresseVille(String ville);
}
