package com.hospital.labload.service;

import com.hospital.labload.event.LabOverloadEvent;
import com.hospital.labload.event.PatientDomainEvent;
import com.hospital.labload.kafka.LabEventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private final AtomicInteger currentLoad = new AtomicInteger(0);

    // Threshold for bottleneck
    private static final int MAX_CAPACITY = 5;

    public void handlePatientEvent(PatientDomainEvent event) {

        if ("SAMPLE_COLLECTED".equals(event.getEventType())) {

            int load = currentLoad.incrementAndGet();

            log.info("Sample received. Current lab load = {}", load);

            if (load > MAX_CAPACITY) {

    log.warn("🚨 LAB BOTTLENECK DETECTED! Load = {}", load);

    LabOverloadEvent overloadEvent =
            new LabOverloadEvent(load, MAX_CAPACITY);

    labEventPublisher.publishOverload(overloadEvent);
}
        }
    }

    public void processPatientEvent(PatientDomainEvent event) {

    // Example logic
    // 1. Track patient movement
    // 2. Measure time spent in department
    // 3. Detect lab queue build-up

    System.out.println(
        "Processing patient event: " + event
    );
}
}
