package com.polytech.alertcovidservicekafka.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private KafkaTemplate<String,Content> kafkaTemplate;

    @Autowired
    KafkaProducer(KafkaTemplate<String,Content> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Content message, String topicName){
        kafkaTemplate.send(topicName,message);
    }
}
