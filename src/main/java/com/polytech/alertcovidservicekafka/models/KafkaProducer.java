package com.polytech.alertcovidservicekafka.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private KafkaTemplate<String, Location> kafkaTemplate;

    @Autowired
    KafkaProducer(KafkaTemplate<String, Location> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Location location, String topicName){
        kafkaTemplate.send(topicName,location);
    }
}
