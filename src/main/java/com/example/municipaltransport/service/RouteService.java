package com.example.municipaltransport.service;

import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public List<Route> findByStartLocation(String startLocation) {
        return routeRepository.findRouteByStartLocation(startLocation);
    }

    public Route save(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> findByEndLocation(String endLocation) {
        return routeRepository.findRouteByEndLocation(endLocation);
    }

    public Optional<Route> findById(Long routeId) {
        return routeRepository.findById(routeId);

    }

    public List<Route> findByIsFavorite() {
        return routeRepository.findByIsFavorite(true);
    }

    public List<Route> findByStartAndEndLocation(String startLocation, String endLocation) {
        List<Route> routes = routeRepository.findRouteByStartAndEndLocation(startLocation, endLocation);
        return routes;
    }
}