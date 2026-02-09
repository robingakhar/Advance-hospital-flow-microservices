package com.hospital.alertservice.kafka;
import com.hospital.alertservice.event.LabOverloadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LabOverloadAlertConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(LabOverloadAlertConsumer.class);

    @KafkaListener(
            topics = "lab-events",
            groupId = "alert-service"
    )
    public void consume(LabOverloadEvent event) {

        log.error(
                "🚨 ALERT: Lab overload detected! Load = {}, Capacity = {}, Time = {}",
                event.getCurrentLoad(),
                event.getMaxCapacity(),
                event.getOccurredAt()
        );

        // Future:
        // sendEmail(event);
        // sendSMS(event);
        // pushDashboardNotification(event);
    }
}