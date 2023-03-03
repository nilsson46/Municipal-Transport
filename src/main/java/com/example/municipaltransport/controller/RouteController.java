package com.example.municipaltransport.controller;

import com.example.municipaltransport.model.Route;
import com.example.municipaltransport.model.TotalRoute;
import com.example.municipaltransport.repository.RouteRepository;
import com.example.municipaltransport.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@RestController
@RequestMapping("/routes/*")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RouteRepository routeRepository;
    private List<Route> routeList;

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes() {
        List<Route> routeList = routeService.getAll();
        System.out.println(routeList);
        return ResponseEntity.ok(routeList);
    }

    @PostMapping
    public ResponseEntity<List<Route>> createRoute(@RequestBody Route route) {
        routeService.save(route);
        return ResponseEntity.status(201).body(routeList);
    }

    @GetMapping("/Start/{startLocation}")
    public List<Route> getRouteByStartLocation(@PathVariable String startLocation) {
        List<Route> routes = routeService.findByStartLocation(startLocation);
        return routes;
    }

    @GetMapping("/End/{endLocation}")
    public List<Route> getRouteByEndLocation(@PathVariable String endLocation) {
        List<Route> routes = routeService.findByEndLocation(endLocation);
        return routes;
    }



   /* @GetMapping("/{startLocation}/to/{endLocation}")
    public ResponseEntity<List<Route>> getRouteByStartAndEndLocation(@PathVariable String startLocation, @PathVariable String endLocation) {
        List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);

        /*if(routeService.(endLocation)){
            routes = routeService.findByEndLocation()
        }

        //Route route = routes.get(0);

        for (Route route : routes) {

            if (route.isStartLocationStation() && route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("Start location and end location are station");
                System.out.println(routes);
                return ResponseEntity.ok(routes);
            } else if (route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("End location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/End/").append(endLocation);
                return getResponse(builder);
            } else if(route.isStartLocationStation()){
                System.out.println("==================================");
                System.out.println("Start location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/Start/").append(startLocation);
                return getResponse(builder);
            }
            else{
                System.out.println("==================================");
                System.out.println("HÄR KOMMER JULIUs DATA");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/car");
                return getResponse(builder);


                // Skapa en model för att slå ihop både min data och gång data för att
                //kunna skicka ut både
            }

        }
        return ResponseEntity.ok(routes);
    }
    private ResponseEntity<List<Route>> getResponse(StringBuilder builder) {
        ResponseEntity<Route> routeResponse = restTemplate.getForEntity(builder.toString(), Route.class);
        List<Route> externalRoutes = Collections.singletonList(routeResponse.getBody());
        System.out.println("==================================");
        System.out.println(builder);
        return ResponseEntity.ok(externalRoutes);
    } */

    // Check if start and end locations are stations

        /*if (!start.isStation() || !end.isStation()) {
            StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
            builder.append("/routes/").append(startLocation).append("/to/").append(endLocation).append("/walk");
            ResponseEntity <Route[]> route = restTemplate.getForEntity(builder.toString(),Route[].class);
            List<Route> routes = Arrays.asList(route.getBody());
            return ResponseEntity.ok(routes);
        } */


       /* List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);
        routes = routeService.findByIsStation(true, startLocation);
        RouteDetails routeDetails = new RouteDetails(routes);

       return ResponseEntity.ok(routeDetails); */


    @GetMapping("/favorites")
    public ResponseEntity<List<Route>> getFavoriteRoutes() {
        List<Route> favoriteRoutes = routeService.findByIsFavorite();
        return ResponseEntity.ok(favoriteRoutes);
    }

    @PutMapping("/{id}/favorite")

    public ResponseEntity<Route> markAsFavorite(@PathVariable Long id) {
        Optional<Route> route = routeService.findById(id);
        if (route.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Route existingRoute = route.get();
        existingRoute.setFavorite(true);

        Route updatedRoute = routeService.save(existingRoute);
        return ResponseEntity.ok(updatedRoute);
    }

    @PutMapping("/{id}/unmark-favorite")

    public ResponseEntity<Route> unmarkAsFavorite(@PathVariable Long id) {
        Optional<Route> route = routeService.findById(id);
        if (route.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Route existingRoute = route.get();
        existingRoute.setFavorite(false);

        Route updatedRoute = routeService.save(existingRoute);
        return ResponseEntity.ok(updatedRoute);
    }

    @PutMapping("/{id}/delay")
    public ResponseEntity<Route> routeDelay(@PathVariable Long id, @RequestBody Map<String, Boolean> delay) {
        boolean newDelay = delay.get("delay");
        Route route = routeService.updateDelay(id, newDelay);
        return ResponseEntity.ok(route);
    }


    @PutMapping("/{id}/delayDescription")
    public ResponseEntity<Route> routeDelayDescription (@PathVariable Long id, @RequestBody Map<String, Integer> description){
         String newDescription = String.valueOf(description.get("description"));
        Route route = routeService.addDescription(id, newDescription);
        return ResponseEntity.ok(route);
    }

      /*@GetMapping("/vehicle")
    public List<RouteInfo> routes() {
        ResponseEntity<List<RouteInfo>> response = restTemplate.exchange(
                "https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io/routes/walk",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<RouteInfo> routes = response.getBody();
        return routes;
    } */

      /* @GetMapping("/{startLocation}/to/{endLocation}")
    public ResponseEntity<List<Route>> getRouteByStartAndEndLocation(@PathVariable String startLocation, @PathVariable String endLocation) {
        List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);

        List<Route> filteredRoutes = routes.stream()
                .filter(route -> route.getStartLocation().)
        return ResponseEntity.ok(routes);
    } */

    @GetMapping("/{startLocation}/to/{endLocation}")
    public ResponseEntity<List<Route>> getRouteByStartAndEndLocation(@PathVariable String startLocation, @PathVariable String endLocation) {
        List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);

        for (Route route : routes) {
            if (route.isStartLocationStation() && route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("Start location and end location are station");
                System.out.println(routes);
                return ResponseEntity.ok(routes);
            } else if (route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("End location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/Start/").append(startLocation);
                return getResponse(builder);
            } else if(route.isStartLocationStation()){
                System.out.println("==================================");
                System.out.println("Start location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/End/").append(endLocation);
                return getResponse(builder);
            } else {
                System.out.println("==================================");
                System.out.println("Fetching external car routes");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes/");
                return getResponse(builder);
            }
        }
        return ResponseEntity.ok(routes);
    }

   /* @GetMapping("/{startLocation}/to/{endLocation}")
    public ResponseEntity<List<Route>> getRouteByStartAndEndLocation(@PathVariable String startLocation, @PathVariable String endLocation){
        List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);
            Route route = new Route();
            if (route.isStartLocationStation() && route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("Start location and end location are station");
                System.out.println(routes);
                return ResponseEntity.ok(null);
            } else if (route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("End location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/End/").append(endLocation);
                return getResponse(builder);
            } else if(route.isStartLocationStation()){
                System.out.println("==================================");
                System.out.println("Start location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/Start/").append(startLocation);
                return getResponse(builder);
            } else {
                System.out.println("==================================");
                System.out.println("Fetching external car routes");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes/");
                return getResponse(builder);
            }
        } */

  /*  @GetMapping("/{startLocation}/to/{endLocation}")
    public ResponseEntity<List<Route>> getRouteByStartAndEndLocation(@PathVariable String startLocation, @PathVariable String endLocation){
        List<Route> routes = routeService.findByStartAndEndLocation(startLocation, endLocation);
        boolean hasDirectRoute = false; // added flag to check if direct route exists

        for (Route route : routes) {
            if (route.isStartLocationStation() && route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("Start location and end location are station");
                System.out.println(routes);
                return ResponseEntity.ok(null);
            } else if (route.isEndLocationStation()) {
                System.out.println("==================================");
                System.out.println("End location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/End/").append(endLocation);
                return getResponse(builder);
            } else if(route.isStartLocationStation()){
                System.out.println("==================================");
                System.out.println("Start location is station");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes").append("/Start/").append(startLocation);
                return getResponse(builder);
            } else {
                System.out.println("==================================");
                System.out.println("Fetching external car routes");
                StringBuilder builder = new StringBuilder("https://microservice-enskild-trafik-enskild-trafik.azuremicroservices.io");
                builder.append("/routes/");
                ResponseEntity<List<Route>> externalRoutesResponse = getResponse(builder); // changed to getResponse method that returns ResponseEntity<List<Route>>
                List<Route> externalRoutes = externalRoutesResponse.getBody();
                if (externalRoutes != null && !externalRoutes.isEmpty()) {
                    for (Route externalRoute : externalRoutes) {
                        if (externalRoute.getStartLocation().equals(startLocation) && externalRoute.getEndLocation().equals(endLocation)) {
                            hasDirectRoute = true;
                            break;
                        }
                    }
                }
                if (hasDirectRoute) { // check if direct route exists
                    return ResponseEntity.ok(externalRoutes); // return external routes if direct route exists
                }
            }
        }

        if (hasDirectRoute) { // check if direct route exists
            return ResponseEntity.ok(routes); // return direct route if it exists
        } else {
            // Return a response indicating no routes were found
            return ResponseEntity.notFound().build();
        }
    }
*/
    private ResponseEntity<List<Route>> getResponse(StringBuilder builder) {
        ResponseEntity<Route[]> routeResponse = restTemplate.getForEntity(builder.toString(), Route[].class); // changed to Route[].class
        List<Route> externalRoutes = Arrays.asList(routeResponse.getBody()); // changed to use Arrays.asList instead of casting
        System.out.println("==================================");
        System.out.println(builder);
        return ResponseEntity.ok(externalRoutes);
    }
}

