package com.polytech.alertcovidservicekafka.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    public final long id_user;
    public final String firstname;
    public final String lastname;
    public final String email;
    public final String phone;

    public User(@JsonProperty("id_user") long id_user, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname, @JsonProperty("email") String email, @JsonProperty("phone") String phone) {
        this.id_user = id_user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }
}