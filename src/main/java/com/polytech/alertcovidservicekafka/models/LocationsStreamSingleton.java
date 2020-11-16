package com.polytech.alertcovidservicekafka.models;

import org.springframework.beans.NullValueInNestedPathException;

import java.sql.Timestamp;
import java.util.LinkedList;

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

    public void setLocations(LinkedList<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location){
        if (location.notNull()) {
            System.out.println("added location : " +  location);
            this.locations.add(location);
        } else {
            //throw new IllegalArgumentException("location with id_user : " + location.getId_user());
        }

    }
}
