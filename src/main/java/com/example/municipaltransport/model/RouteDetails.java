package com.example.municipaltransport.model;

import lombok.Data;

import java.util.List;

@Data
public class RouteDetails {
    private List<Route> routes;

    public RouteDetails (List<Route> routes){
        this.routes = routes;
    }
}
