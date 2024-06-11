package com.users.users.repository;

import com.users.users.models.GasRetailer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerRepository extends JpaRepository<GasRetailer,Integer> {
}
