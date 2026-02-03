package com.hospital.patientflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientId;
    private String visitId;
    private String eventType;
    private LocalDateTime eventTime;

    // getters and setters
}
