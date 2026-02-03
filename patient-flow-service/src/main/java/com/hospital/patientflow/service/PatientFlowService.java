package com.hospital.patientflow.service;

import com.hospital.patientflow.dto.PatientEventRequest;
import com.hospital.patientflow.model.PatientEventEntity;
import com.hospital.patientflow.repository.PatientEventRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientFlowService {

    private final PatientEventRepository repository;

    public PatientFlowService(PatientEventRepository repository) {
        this.repository = repository;
    }

    public void processEvent(PatientEventRequest request) {
        PatientEventEntity entity = new PatientEventEntity();
        entity.setPatientId(request.getPatientId());
        entity.setVisitId(request.getVisitId());
        entity.setEventType(request.getEventType());
        entity.setEventTime(request.getEventTime());

        repository.save(entity);
    }
}
