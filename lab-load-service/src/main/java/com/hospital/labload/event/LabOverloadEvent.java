package com.hospital.labload.event;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabOverloadEvent {

    private String eventType;      // LAB_OVERLOAD
    private int currentLoad;
    private int maxCapacity;
    private Instant occurredAt;
    private String sourceService;

    public LabOverloadEvent() {
    }

    public LabOverloadEvent(int currentLoad, int maxCapacity) {
        this.eventType = "LAB_OVERLOAD";
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.occurredAt = Instant.now();
        this.sourceService = "lab-load-service";
    }

    // getters & setters
}
