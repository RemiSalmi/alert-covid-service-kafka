package com.polytech.alertcovidservicekafka.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class Location {
    @Id @NotNull
    private long id_user;
    @Id @NotNull
    private Timestamp date;
    @Id @NotNull
    private Double longitude;
    @Id @NotNull
    private Double latitude;

    public Location(long id_user, Timestamp LocalDateTime, Double longitude, Double latitude) {
        this.id_user = id_user;
        this.date = LocalDateTime;
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

    @Override
    public String toString() {
        return "Location{" +
                "id_user=" + id_user +
                ", date=" + date +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Java object to JSON string
        return mapper.writeValueAsString(this);
    }

    public String toJsonForLocationService() {
        String jsonLocation = "{ \"idUser\":" + this.getId_user() +
                ",\"date\": \"" + this.getDate().toLocalDateTime() +"\"" +
                ",\"longitude\": " + this.getLongitude() +
                ",\"latitude\": " + this.getLatitude() + "}";
        return jsonLocation;
    }

    public boolean notNull() {
        return (this.date != null && this.latitude != null && this.longitude != null);
    }
}
