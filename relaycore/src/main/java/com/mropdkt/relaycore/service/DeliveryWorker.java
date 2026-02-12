package com.mropdkt.relaycore.service;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mropdkt.relaycore.dto.WebhookRequest;
import com.mropdkt.relaycore.entity.WebHookEvent;
import com.mropdkt.relaycore.repository.EventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryWorker {

    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;
    private final RestClient restClient = RestClient.create();

    @RetryableTopic(attempts ="3", backoff = @Backoff(delay = 2000, multiplier = 2.0), exclude = {IllegalArgumentException.class})
    @KafkaListener(topics = "webhook-inbound", groupId = "relaycore-group", concurrency="5")
    public void process(String message){
        try {
            WebhookRequest request = objectMapper.readValue(message, WebhookRequest.class);
            log.info("Relaying to: {}", request.url());

            restClient.post().uri(request.url()).body(request.data()).retrieve().toBodilessEntity();
            saveStatus(request, "SUCCESS", null);
        } catch (Exception e) {
            log.error("Delivery Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @DltHandler
    public void handleDlt(String message){
        log.error("FATAL: Moving to Dead Letter Topic");
        try {
            WebhookRequest request = objectMapper.readValue(message, WebhookRequest.class);
            saveStatus(request, "FAILED", "Exceeded retry attempts");
        } catch (Exception e) {
            log.error("Dlt Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void saveStatus(WebhookRequest req, String status, String error){
        WebHookEvent event = WebHookEvent.builder().targetUrl(req.url()).payload(req.data()).status(status).lastErrorMessage(error).build();
        eventRepository.save(event);
    }
}
