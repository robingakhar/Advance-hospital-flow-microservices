package com.hospital.patientflow.repository;

import com.hospital.patientflow.model.PatientEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientEventRepository
        extends JpaRepository<PatientEventEntity, Long> {
}
