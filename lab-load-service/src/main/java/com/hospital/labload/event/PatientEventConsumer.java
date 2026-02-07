package com.hospital.labload.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.hospital.labload.service.LabLoadService;

@Component
public class PatientEventConsumer {

    private final LabLoadService labLoadService;

    public PatientEventConsumer(LabLoadService labLoadService) {
        this.labLoadService = labLoadService;
    }

    @KafkaListener(
        topics = "patient-events",
        groupId = "lab-load-service"
    )
    public void consume(PatientDomainEvent event) {

        // This method is called automatically by Kafka
        // whenever a new patient event arrives

        labLoadService.handlePatientEvent(event);
    }
}