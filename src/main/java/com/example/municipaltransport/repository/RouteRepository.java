package com.example.municipaltransport.repository;

import com.example.municipaltransport.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findRouteByStartLocation(String startLocation);
    List<Route> findRouteByEndLocation (String endLocation);
    List<Route> findByIsFavorite(boolean b);
    List<Route> findRouteByStartLocationAndEndLocation(String startLocation, String endLocation);
}
