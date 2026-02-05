package com.hospital.patientflow.event;

import com.hospital.patientflow.event.PatientDomainEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientEventPublisher {

    private static final String TOPIC_NAME = "patient-events";

    private final KafkaTemplate<String, PatientDomainEvent> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Value("${event.publish.kafka.enabled:true}")
    private boolean kafkaEnabled;

    @Value("${event.publish.rest.enabled:false}")
    private boolean restEnabled;

    @Value("${lab.load.service.url:http://localhost:8082/events}")
    private String labLoadServiceUrl;

    public PatientEventPublisher(
            KafkaTemplate<String, PatientDomainEvent> kafkaTemplate,
            RestTemplate restTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.restTemplate = restTemplate;
    }

    /**
     * Publish patient domain event.
     * REST and Kafka are controlled via feature flags.
     */
    public void publish(PatientDomainEvent event) {

        // 1️⃣ Publish to Kafka (ASYNC, preferred)
        if (kafkaEnabled) {
            kafkaTemplate.send(
                    TOPIC_NAME,
                    event.getPatientId(),   // key for ordering
                    event
            );
        }

        // 2️⃣ Optional REST publishing (legacy, disabled by default)
        if (restEnabled) {
            try {
                restTemplate.postForEntity(
                        labLoadServiceUrl,
                        event,
                        Void.class
                );
            } catch (Exception ex) {
                // REST failure should NOT break main flow
                // Log and move on
                System.err.println(
                        "REST publish failed, Kafka unaffected: " + ex.getMessage()
                );
            }
        }
    }
}
