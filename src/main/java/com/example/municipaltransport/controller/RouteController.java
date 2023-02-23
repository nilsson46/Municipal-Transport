package com.example.municipaltransport.controller;

import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.service.RouteService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    private List<Route> routeList;

    @GetMapping("channels")
    public ResponseEntity<List<Route>> getRoutes(){
        List<Route> routeList = routeService.getAll();
        return ResponseEntity.ok(routeList);
    }
    
}
