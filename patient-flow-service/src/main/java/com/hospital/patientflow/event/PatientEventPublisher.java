package com.hospital.patientflow.event;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(PatientEventPublisher.class);

    private final RestTemplate restTemplate;

    public PatientEventPublisher(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(
        name = "labLoadService",
        fallbackMethod = "fallbackPublish"
    )
    public void publish(PatientDomainEvent event) {

        log.info("Publishing patient event: {}", event);

        restTemplate.postForEntity(
                "http://localhost:8082/api/v1/lab-events",
                event,
                Void.class
        );
    }

    // Fallback method
    public void fallbackPublish(PatientDomainEvent event, Exception ex) {
        log.error("Lab Load Service unavailable. Event skipped: {}", event.getEventId());
    }
}
