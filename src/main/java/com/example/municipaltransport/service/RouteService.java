package com.example.municipaltransport.service;

import com.example.municipaltransport.exception.ExceptionHandler;
import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.repository.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Log4j2
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    RestTemplate restTemplate;

    public List<Route> getAll() {
        log.debug("All courses is being fetched");
        return routeRepository.findAll();
    }

    public List<Route> findByStartLocation(String startLocation) {
        return routeRepository.findRouteByStartLocation(startLocation);
    }

    public Route save(Route route) {
        log.debug("Route got saved");
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

    public Route updateDelay(Long id, int delay) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new ExceptionHandler(id));
        route.setDelay(delay);
        log.debug("Delay was updated");
        return routeRepository.save(route);
    }

    public Route addDescription(Long id, String description) {
        Route route = routeRepository.findById(id).orElseThrow(() -> new ExceptionHandler(id));

        route.setDelayDescription(description);
        log.debug("Delay description was made");
        return routeRepository.save(route);
    }

    public List<Route> findByStartAndEndLocation(String startLocation, String endLocation) {
        List<Route> routes = routeRepository.findByStartLocationAndEndLocation(startLocation, endLocation);

        for(Route route : routes) {
            if (route.isStartLocationStation() && route.isEndLocationStation()) {
               log.info("Start location and end location are stations");
                return routes;
            } else if (!route.isStartLocationStation() && !route.isEndLocationStation()) {
                log.info("Walk route from Julius because none of the locations are stations");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes/").append("katrineholm").append("/to/").append("stockholm").append("/walk");
                return getRoutes(routes, builder);
            } else if (route.isEndLocationStation()) {
                log.info("End location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/Start/").append(startLocation);
                return getRoutes(routes, builder);
            } else if (route.isStartLocationStation()) {
                log.info("Start location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/End/").append(endLocation);
                return getRoutes(routes, builder);
            }
        }

        return routes;
    }

    public List<Route> getRoutes(List<Route> routes, StringBuilder builder) {
        ResponseEntity<List<Route>> externalRoutesResponse = getExternalRoutes(builder);
        List<Route> externalRoutes = externalRoutesResponse.getBody();
        assert externalRoutes != null;
        routes.addAll(externalRoutes);
        return routes;
    }

    private ResponseEntity<List<Route>> getExternalRoutes(StringBuilder builder) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Route[]> routeResponse = restTemplate.getForEntity(builder.toString(), Route[].class);
        List<Route> externalRoutes = Arrays.asList(Objects.requireNonNull(routeResponse.getBody()));
        return ResponseEntity.ok(externalRoutes);
    }
}