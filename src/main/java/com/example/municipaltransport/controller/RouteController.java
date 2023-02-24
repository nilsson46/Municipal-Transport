package com.example.municipaltransport.controller;

import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    private List<Route> routeList;

    @GetMapping("routes")
    public ResponseEntity<List<Route>> getRoutes(){
        List<Route> routeList = routeService.getAll();
        return ResponseEntity.ok(routeList);
    }

    @PostMapping("routes")
    public ResponseEntity<List<Route>> createRoute(@RequestBody Route route){
        routeService.save(route);
        return ResponseEntity.status(201).body(routeList);
    }

    @GetMapping("routes/Start/{startLocation}")
    public List<Route> getRouteByStartLocation(@PathVariable String startLocation){
       List<Route> routes = routeService.findByStartLocation(startLocation);
       return routes;
    }

    @GetMapping("routes/End/{endLocation}")
    public List<Route> getRouteByEndLocation(@PathVariable String endLocation){
        List<Route> routes = routeService.findByEndLocation(endLocation);
        return routes;
    }

    @GetMapping("routes/favorites")
    public ResponseEntity<List<Route>> getFavoriteRoutes(){
        List<Route> favoriteRoutes = routeService.findByIsFavorite();
        return ResponseEntity.ok(favoriteRoutes);
    }

    @PutMapping("routes/{id}/favorite")

    public ResponseEntity<Route> markAsFavorite (@PathVariable Long id){
        Optional<Route> route = routeService.findById(id);
        if(route.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Route existingRoute = route.get();
        existingRoute.setFavorite(true);

        Route updatedRoute = routeService.save(existingRoute);
        return ResponseEntity.ok(updatedRoute);
    }
}
