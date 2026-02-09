package com.hospital.labload.kafka;

import com.hospital.labload.event.LabOverloadEvent;
import com.hospital.labload.event.SlaBreachEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LabEventPublisher {

    private static final String TOPIC = "lab-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LabEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOverload(LabOverloadEvent event) {
        System.out.println(event.toString());
        kafkaTemplate.send(TOPIC, "LAB_OVERLOAD", event);
    }

    public void publishSlaBreach(SlaBreachEvent event) {
        kafkaTemplate.send(TOPIC, "SLA_BREACH", event);
    }
}
