package com.mropdkt.relaycore.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mropdkt.relaycore.entity.WebHookEvent;


public interface EventRepository extends JpaRepository<WebHookEvent, UUID> {
    List<WebHookEvent> findByStatus(String status);

    List<WebHookEvent> findByTargetUrlContaining(String urlPart);
}
