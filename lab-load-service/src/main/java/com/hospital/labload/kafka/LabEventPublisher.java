package com.hospital.labload.kafka;

import com.hospital.labload.event.LabOverloadEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LabEventPublisher {

    private static final String TOPIC = "lab-events";

    private final KafkaTemplate<String, LabOverloadEvent> kafkaTemplate;

    public LabEventPublisher(KafkaTemplate<String, LabOverloadEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOverload(LabOverloadEvent event) {
        System.out.println(event.toString());
        kafkaTemplate.send(TOPIC, "LAB_OVERLOAD", event);
    }
}
