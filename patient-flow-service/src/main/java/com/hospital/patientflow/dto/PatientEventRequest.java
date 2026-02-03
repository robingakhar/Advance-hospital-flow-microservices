package com.hospital.patientflow.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientEventRequest {

    private String patientId;
    private String visitId;
    private String eventType;
    private LocalDateTime eventTime;

    // getters and setters
}
