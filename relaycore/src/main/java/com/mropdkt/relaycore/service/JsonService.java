package com.mropdkt.relaycore.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mropdkt.relaycore.dto.WebhookRequest;

@Service
public class JsonService {
    private final ObjectMapper objectMapper;
    
    public JsonService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    
    public WebhookRequest parse(String json){
        try {
            return objectMapper.readValue(json, WebhookRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
