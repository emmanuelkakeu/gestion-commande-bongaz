package com.users.users.repository;

import com.users.users.models.IndividualClient;
import com.users.users.services.IndividualClientServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualClientRepository extends JpaRepository<IndividualClient, Integer> {
}
