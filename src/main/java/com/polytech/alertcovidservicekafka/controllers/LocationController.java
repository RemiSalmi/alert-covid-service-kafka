package com.polytech.alertcovidservicekafka.controllers;



import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import com.polytech.alertcovidservicekafka.models.Location;
import com.polytech.alertcovidservicekafka.models.KafkaProducer;
import com.polytech.alertcovidservicekafka.models.LocationsStreamSingleton;
import com.polytech.alertcovidservicekafka.services.LocationLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/stream/location")
public class LocationController {

    @Autowired
    KafkaProducer kafkaProducer;

    private LocationsStreamSingleton locationsStream = LocationsStreamSingleton.getInstance();


    @PostMapping
    public String create(@RequestBody Location location){
        System.out.println("Start");
        System.out.println(location);
        kafkaProducer.sendMessage(location, "yxffg513-covid_alert");
        System.out.println("Fin");
        return "Publish";
    }

    @GetMapping
    public Double getLocationStream() {
        //return locationsStream.getLocations();

        Coordinate lat = Coordinate.fromDegrees(43.62702);
        Coordinate lng = Coordinate.fromDegrees(3.8708548);
        Point kew = Point.at(lat, lng);


        lat = Coordinate.fromDegrees(43.6272562);
        lng = Coordinate.fromDegrees(3.8723416);
        Point richmond = Point.at(lat, lng);

        double distance = EarthCalc.gcd.distance(richmond, kew); //in meters
        return distance;
    }

    @GetMapping(value = "/test")
    public LinkedList<Location> testing() {
        return locationsStream.getLocations();
    }

    @PostMapping(value="/positive")
    public List<Location> test(@RequestBody int declaredPositiveUserId, Timestamp declaredPositiveDate) {
        System.out.println("test");
        LocationLogic locationLogic = new LocationLogic();
        List<Location> contactLocation = locationLogic.getContactLocation(locationsStream.getLocations(), declaredPositiveUserId, declaredPositiveDate);
        return contactLocation;
    }
}
