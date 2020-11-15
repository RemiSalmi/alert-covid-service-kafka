package com.polytech.alertcovidservicekafka.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Id;
import java.sql.Timestamp;

public class Location {
    @Id
    private long id_user;
    @Id
    private Timestamp date;
    @Id
    private Double longitude;
    @Id
    private Double latitude;

    public Location(long id_user, Timestamp date, Double longitude, Double latitude) {
        this.id_user = id_user;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Location() {
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String toString(){
        return "Je suis l'user avec l'id: " + this.getId_user() +"\nDate: " +this.getDate();
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Java object to JSON string
        return mapper.writeValueAsString(this);
    }
}
