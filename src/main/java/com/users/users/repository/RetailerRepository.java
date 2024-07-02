package com.users.users.repository;

import com.users.users.models.GasRetailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RetailerRepository extends JpaRepository<GasRetailer,Integer> {

    @Query(value = "SELECT * FROM user_account " +
            "WHERE dtype = 'GasRetailer' " +
            "ORDER BY ST_Distance(ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), " +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)) LIMIT 10",
            nativeQuery = true)
    List<GasRetailer> findNearbyRetailers(@Param("lat") double lat, @Param("lng") double lng);
}
