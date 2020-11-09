package com.polytech.alertcovidservicekafka.models;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
@KafkaListener(id = "class-level", topics = "yxffg513-covid_alert")
public class KafkaConsumer {
    /*@KafkaHandler
    void listen(String message) {
        Logger logger = Logger.getLogger("MyLogger1");
        logger.info("KafkaHandler[String] " + message);
    }*/

    @KafkaHandler(isDefault = true)
    void listenDefault(Object object) {
        Logger logger = Logger.getLogger("MyLogger2");
        logger.info("KafkaHandler[Default] " + object);
    }

    @KafkaListener(
            groupId = "my_topic",
            topicPartitions = @TopicPartition(
                    topic = "yxffg513-covid_alert",
                    partitionOffsets = {@PartitionOffset(
                            partition = "0",
                            initialOffset = "0")}))
    void listenToPartitionWithOffset(
            @Payload Content message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset,
            @Headers MessageHeaders headers
            ) {
        Logger logger = Logger.getLogger("MyLogger3");
        logger.info("Received message [" + message + "] from partition-" + partition + " with offset-" + offset);
    }
    /*
    @KafkaListener(topics = "yxffg513-covid_alert", groupId = "my-topic")
    void listenToPartitionWithOffset2(
            ConsumerRecord<String, Content> record,
            Acknowledgment acknowledgment) {
        Logger logger = Logger.getLogger("MyLogger4");
        logger.info("Listener 2 - Received message ["+record.value()+"] from partition-"+record.partition()+" with offset-"+record.offset()+" recorded at: "+ new Date(record.timestamp()));
        acknowledgment.acknowledge();
    }*/

}
