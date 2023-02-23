package com.example.municipaltransport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Entity
public class Route {

    @Id
    @GeneratedValue
    private long id;
    private String startLocation;
    private String endLocation;
    private int departure;
    private int arrival;
    private int travelTime;
    private int changes;
    private boolean favorite; 
}
