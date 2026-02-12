package com.mropdkt.relaycore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mropdkt.relaycore.service.IngestionSevice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/relay")
@RequiredArgsConstructor
public class WebhookController {
    private final IngestionSevice ingestionSevice;

    @PostMapping
    public ResponseEntity<String> handle(@RequestBody String payload){
        ingestionSevice.publishEvent(payload);
        return ResponseEntity.accepted().body("RelayCore: Processed");
    }
}
