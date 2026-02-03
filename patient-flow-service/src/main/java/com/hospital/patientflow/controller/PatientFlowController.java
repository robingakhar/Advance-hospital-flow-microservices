package com.hospital.patientflow.controller;

import com.hospital.patientflow.dto.PatientEventRequest;
import com.hospital.patientflow.service.PatientFlowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient-events")
public class PatientFlowController {

    private final PatientFlowService service;

    public PatientFlowController(PatientFlowService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createEvent(
            @RequestBody PatientEventRequest request) {

        service.processEvent(request);
        return ResponseEntity.accepted().build();
    }
}
