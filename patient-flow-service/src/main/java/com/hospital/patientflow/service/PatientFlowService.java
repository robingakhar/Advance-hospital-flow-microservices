package com.hospital.patientflow.service;

import com.hospital.patientflow.dto.PatientEventRequest;
import com.hospital.patientflow.model.PatientEventEntity;
import com.hospital.patientflow.repository.PatientEventRepository;
import com.hospital.patientflow.event.PatientEventPublisher;
import com.hospital.patientflow.event.PatientDomainEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientFlowService {

    private final PatientEventRepository repository;
    private PatientEventPublisher publisher;

@Autowired
    public PatientFlowService(PatientEventRepository repository,PatientEventPublisher publisher) {
        this.repository = repository;
          this.publisher = publisher;
    }

    public void processEvent(PatientEventRequest request) {
        PatientEventEntity entity = new PatientEventEntity();
        entity.setPatientId(request.getPatientId());
        entity.setVisitId(request.getVisitId());
        entity.setEventType(request.getEventType());
        entity.setEventTime(request.getEventTime());

        repository.save(entity);
         // Publish domain event
        PatientDomainEvent event = new PatientDomainEvent(
                UUID.randomUUID().toString(),
                request.getEventType(),
                "patient-flow-service",
                Instant.now(),
                request.getPatientId(),
                request.getVisitId()
        );
System.out.println(event.toString());
        publisher.publish(event);
        System.out.println(">>> Event sent to Kafka: " + event);

    }
}
