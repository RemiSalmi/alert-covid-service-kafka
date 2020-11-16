package com.polytech.alertcovidservicekafka.models;

import org.springframework.beans.NullValueInNestedPathException;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class LocationsStreamSingleton {

    private static LocationsStreamSingleton INSTANCE;
    private LinkedList<Location> locations;

    private LocationsStreamSingleton() {
        this.locations = new LinkedList<>();
    }

    public static LocationsStreamSingleton getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LocationsStreamSingleton();
        }
        return INSTANCE;
    }

    public LinkedList<Location> getLocations() {
        return locations;
    }

    public List<Location> getUserLocations(Long id_user) {
        return locations.stream().filter(location -> id_user.equals(location.getId_user())).collect(Collectors.toList());
    }

    public void setLocations(LinkedList<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location){
        if (location.notNull()) {
            this.locations.add(location);
        } else {
            //throw new IllegalArgumentException("location with id_user : " + location.getId_user());
        }

    }
}
