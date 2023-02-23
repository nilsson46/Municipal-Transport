package com.example.municipaltransport.repository;

import com.example.municipaltransport.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findRouteByStartLocation(String startLocation);
    Route findRouteByArrival(String arrival);
}
