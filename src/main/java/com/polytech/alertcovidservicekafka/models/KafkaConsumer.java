package com.polytech.alertcovidservicekafka.models;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.logging.Logger;

@Component
@KafkaListener(id = "class-level", topics = "yxffg513-covid_alert")
public class KafkaConsumer {

    private LocationsStreamSingleton LocationsStream = LocationsStreamSingleton.getInstance();

    @KafkaListener(
            groupId = "my_topic",
            topicPartitions = @TopicPartition(
                    topic = "yxffg513-covid_alert",
                    partitionOffsets = {@PartitionOffset(
                            partition = "0",
                            initialOffset = "0")}))
    void listenToPartitionWithOffsetAndDate(
            @Payload Location location,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset,
            @Header(name= KafkaHeaders.RECEIVED_MESSAGE_KEY, required = false) Integer key,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP)long time
    ) {
        this.LocationsStream.addLocation(location);
        Logger logger = Logger.getLogger("MyLogger4");
        Timestamp timestamp = new Timestamp(time);
        logger.info("Key: "+key+"\nMessage : " + location +"\nTimestamp: " + timestamp);
    }


}
