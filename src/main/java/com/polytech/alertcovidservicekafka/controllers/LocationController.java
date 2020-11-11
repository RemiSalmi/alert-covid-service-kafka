package com.polytech.alertcovidservicekafka.controllers;



import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

import com.polytech.alertcovidservicekafka.models.Location;
import com.polytech.alertcovidservicekafka.models.KafkaProducer;
import com.polytech.alertcovidservicekafka.models.LocationsStreamSingleton;
import com.polytech.alertcovidservicekafka.models.PositiveCase;
import com.polytech.alertcovidservicekafka.services.LocationLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/stream/location")
public class LocationController {

    @Autowired
    KafkaProducer kafkaProducer;

    private LocationsStreamSingleton locationsStream = LocationsStreamSingleton.getInstance();

    @GetMapping(value = "/test")
    public LinkedList<Location> getLocation() {
        return locationsStream.getLocations();
    }

    @PostMapping
    public String create(@RequestBody Location location) {
        System.out.println("Start");
        System.out.println(location);
        kafkaProducer.sendMessage(location, "yxffg513-covid_alert");
        System.out.println("Fin");
        return "Publish";
    }

    @PostMapping(value="/positive")
    public List<Location> getContactCase(@RequestBody PositiveCase positiveCase) {
        LocationLogic locationLogic = new LocationLogic();
        List<Location> contactLocation = locationLogic.getContactLocation(locationsStream.getLocations(), positiveCase.getId_user(), positiveCase.getDate());
        return contactLocation;
    }
}
