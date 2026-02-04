package com.hospital.labload.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientEventPublisher {

    private static final Logger log =
            LoggerFactory.getLogger(PatientEventPublisher.class);

    public void publish(PatientDomainEvent event) {
        // For now, just log the event
        log.info("Publishing patient event: {}", event);
    }
}
