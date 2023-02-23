package com.example.municipaltransport.service;

import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;

    public List<Route> getAll(){
        return routeRepository.findAll();
    }
    public Route findByStartLocation(String startLocation){
        return routeRepository.findRouteByStartLocation(startLocation);
    }

}
