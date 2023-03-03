package com.example.municipaltransport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@ToString
@Entity
public class Route {

    @Id
    @GeneratedValue
    private long id;
    private String startLocation;
    private String endLocation;
    private String departure;
    private String arrival;
    private int travelTime;
    private int delay;
    private String delayDescription;
    private int changes;
    private boolean isFavorite;
    private boolean isStartLocationStation;
    private boolean isEndLocationStation;
}
