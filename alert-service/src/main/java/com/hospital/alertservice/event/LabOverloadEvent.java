package com.hospital.alertservice.event;

import lombok.Data;

import java.time.Instant;

@Data
public class LabOverloadEvent {

    private String eventType;
    private int currentLoad;
    private int maxCapacity;
    private Instant occurredAt;
    private String sourceService;

    public LabOverloadEvent() {}

    // getters & setters
}
