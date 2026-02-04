package com.hospital.labload.controller;

import com.hospital.labload.event.PatientDomainEvent;
import com.hospital.labload.service.LabLoadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lab-events")
public class LabEventController {

    private final LabLoadService labLoadService;

    public LabEventController(LabLoadService labLoadService) {
        this.labLoadService = labLoadService;
    }

    @PostMapping
    public ResponseEntity<Void> receiveEvent(
            @RequestBody PatientDomainEvent event) {

        labLoadService.handlePatientEvent(event);
        return ResponseEntity.accepted().build();
    }
}
