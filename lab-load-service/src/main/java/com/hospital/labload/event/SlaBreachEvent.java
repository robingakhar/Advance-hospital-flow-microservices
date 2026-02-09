package com.hospital.labload.event;

import java.time.Instant;

public class SlaBreachEvent {

    private String eventType;              // SLA_BREACH
    private String patientId;
    private String visitId;
    private long actualDurationSeconds;
    private long slaLimitSeconds;
    private Instant occurredAt;
    private String sourceService;

    public SlaBreachEvent() {
    }

    public SlaBreachEvent(
            String patientId,
            String visitId,
            long actualDurationSeconds,
            long slaLimitSeconds
    ) {
        this.eventType = "SLA_BREACH";
        this.patientId = patientId;
        this.visitId = visitId;
        this.actualDurationSeconds = actualDurationSeconds;
        this.slaLimitSeconds = slaLimitSeconds;
        this.occurredAt = Instant.now();
        this.sourceService = "lab-load-service";
    }

    public String getEventType() {
        return eventType;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getVisitId() {
        return visitId;
    }

    public long getActualDurationSeconds() {
        return actualDurationSeconds;
    }

    public long getSlaLimitSeconds() {
        return slaLimitSeconds;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getSourceService() {
        return sourceService;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public void setActualDurationSeconds(long actualDurationSeconds) {
        this.actualDurationSeconds = actualDurationSeconds;
    }

    public void setSlaLimitSeconds(long slaLimitSeconds) {
        this.slaLimitSeconds = slaLimitSeconds;
    }

    public void setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }   }