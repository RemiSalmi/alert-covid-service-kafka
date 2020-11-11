package com.polytech.alertcovidservicekafka.models;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

public class PositiveCase {

    @Id
    private Long id_user;
    private Timestamp date;

    public PositiveCase(Long id_user, Timestamp date) {
        this.id_user = id_user;
        this.date = date;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PositiveCase{" +
                "id_user=" + id_user +
                ", date=" + date +
                '}';
    }
}
