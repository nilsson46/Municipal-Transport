package com.example.municipaltransport.model;

import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WalkRoute {
    private String startLocation;
    private String endLocation;
    private int travelTime;
    // HÄMTA JULIUS DATA!
}
