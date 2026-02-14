package com.mropdkt.relaycore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mropdkt.relaycore.service.IngestionSevice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/relay")
@RequiredArgsConstructor
@Tag(name = "RelayEngine", description = "Endpoints for webhook relay")
public class WebhookController {
    private final IngestionSevice ingestionSevice;
    @Operation(summary = "Ingest a new webhook", description = "Accepts a payload and queues it for delivery via Kafka")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "Event accepted and queued"),
        @ApiResponse(responseCode = "400", description = "Invalid payload format")
    })
    @PostMapping
    public ResponseEntity<String> handle(@RequestBody String payload){
        ingestionSevice.publishEvent(payload);
        return ResponseEntity.accepted().body("RelayCore: Processed");
    }
}
