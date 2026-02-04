package com.hospital.labload.event;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientDomainEvent {

    private String eventId;
    private String eventType;
    private String sourceService;
    private LocalDateTime occurredAt;

    private String patientId;
    private String visitId;
}
