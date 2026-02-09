package com.hospital.labload.service;

import com.hospital.labload.event.LabOverloadEvent;
import com.hospital.labload.event.PatientDomainEvent;
import com.hospital.labload.event.SlaBreachEvent;
import com.hospital.labload.kafka.LabEventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LabLoadService {

    private static final Logger log =
            LoggerFactory.getLogger(LabLoadService.class);
@Autowired
LabEventPublisher labEventPublisher ;
    // Simulated lab queue size
    LabLoadService(LabEventPublisher labEventPublisher){
        this.labEventPublisher=labEventPublisher;
    }


    // Threshold for bottleneck
    private static final long SAMPLE_TO_TEST_SLA_SECONDS = 10; // 10 minutes

    private final AtomicInteger currentLoad = new AtomicInteger(0);
    private final Map<String, Instant> sampleCollectedMap = new ConcurrentHashMap<>();
    private static final int MAX_CAPACITY = 5;
    public void handlePatientEvent(PatientDomainEvent event) {
        String key = event.getPatientId() + "_" + event.getVisitId();
        if ("SAMPLE_COLLECTED".equals(event.getEventType())) {
            sampleCollectedMap.put(key, event.getOccurredAt());
            int load = currentLoad.incrementAndGet();

            log.info("Sample received. Current lab load = {}", load);

            if (load > MAX_CAPACITY) {

    log.warn("🚨 LAB BOTTLENECK DETECTED! Load = {}", load);

    LabOverloadEvent overloadEvent =
            new LabOverloadEvent(load, MAX_CAPACITY);

    labEventPublisher.publishOverload(overloadEvent);
}
        }
        // ---------------- TEST_STARTED ----------------
        if ("TEST_STARTED".equals(event.getEventType())) {

            Instant collectedAt = sampleCollectedMap.get(key);

            if (collectedAt != null) {

                long durationSeconds =
                        Duration.between(collectedAt, event.getOccurredAt())
                                .getSeconds();

                if (durationSeconds > SAMPLE_TO_TEST_SLA_SECONDS) {

                    log.warn("🚨 SLA BREACH detected for {}", key);

                    SlaBreachEvent slaEvent =
                            new SlaBreachEvent(
                                    event.getPatientId(),
                                    event.getVisitId(),
                                    durationSeconds,
                                    SAMPLE_TO_TEST_SLA_SECONDS
                            );

                    labEventPublisher.publishSlaBreach(slaEvent);
                }
            }
        }
    }
    }



