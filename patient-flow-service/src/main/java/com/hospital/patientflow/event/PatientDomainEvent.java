package com.hospital.patientflow.event;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PatientDomainEvent {

    private String eventId;
    private String eventType;
    private String sourceService;
    private Instant occurredAt;

    private String patientId;
    private String visitId;
}
