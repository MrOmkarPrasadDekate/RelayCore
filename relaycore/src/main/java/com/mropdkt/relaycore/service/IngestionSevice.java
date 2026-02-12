package com.mropdkt.relaycore.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngestionSevice {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "webhook-inbound";

    public void publishEvent(String payload){
        kafkaTemplate.send(TOPIC, payload).whenComplete((result, ex) -> {
            if(ex != null){
                log.error("Kafka Publish Failed: {}", ex.getMessage());
            }else{
                log.info("Queued in Kafka: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
