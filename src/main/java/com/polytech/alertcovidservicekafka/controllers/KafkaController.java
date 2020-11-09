package com.polytech.alertcovidservicekafka.controllers;

import com.polytech.alertcovidservicekafka.models.Content;
import com.polytech.alertcovidservicekafka.models.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping(value = "/publish")
    public String publishMessage(@RequestBody @Valid Content content){
        System.out.println("Start");
        System.out.println(content);
        kafkaProducer.sendMessage(content, "yxffg513-covid_alert");
        System.out.println("Fin");
        return "Publish";
    }
}
