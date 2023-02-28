package com.example.municipaltransport.service;

import com.example.municipaltransport.exception.ExceptionHandler;
import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.repository.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    /*public RouteService (RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }*/

    public List<Route> getAll() {
        log.debug("All courses is being fetched");
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
        log.info("Didn't find a route");
        List<Route> routes = routeRepository.findRouteByStartLocationAndEndLocation(startLocation, endLocation);
        return routes;
    }

    public Route updateDelay(Long id, int delay) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new ExceptionHandler(id));

        route.setDelay(delay);
        return routeRepository.save(route);
    }
}