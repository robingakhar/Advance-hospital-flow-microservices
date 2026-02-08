Kafka Setup & Event-Driven Communication Guide

Advanced Hospital Flow & Lab Bottleneck Optimization System

1. Why Kafka in This Project

Initially, services communicated using REST + Resilience4j.
This approach had limitations:

Tight coupling between services

No real-time observability

Synchronous failures impacted patient flow

Not suitable for analytics or alerts

Why Kafka?

Kafka enables:

Asynchronous, event-driven communication

Loose coupling between microservices

Real-time monitoring and intelligence

Scalable consumers (alerts, analytics, optimization)

Rule followed:

REST for commands, Kafka for state-change events

2. Kafka Setup Using Docker (KRaft Mode)

Kafka is run using Docker, avoiding local Windows/KRaft issues.

Why Docker?

No ZooKeeper or KRaft setup pain on Windows

Reproducible environment

Easy cleanup and reset

Kafka Version

Apache Kafka 3.x

Single-node setup (sufficient for development)

3. Start Kafka Using Docker
Folder structure
kafka-docker/
 └── docker-compose.yml

Start Kafka
docker compose up -d

Verify Kafka is running
docker ps


Kafka broker runs on:

localhost:9092

4. Kafka Topics Used
Topic Name	Purpose
patient-events	Patient flow domain events
lab-events	Derived lab intelligence events
5. Kafka Topic Commands
Create a topic
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --create \
  --topic patient-events \
  --partitions 3 \
  --replication-factor 1

List topics
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --list

Describe a topic
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --topic patient-events

Delete a topic
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --delete \
  --topic patient-events

6. Event Publishing (Producer)
Example: Patient Flow Service

Dependencies

<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>

Producer configuration

Spring Boot auto-configures producer when using:

spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer

Publishing an event
kafkaTemplate.send("patient-events", event);

7. Event Consumption (Consumer)
Why custom consumer config?

Default Kafka deserialization caused issues:

Class mismatch across services

Java Time (LocalDateTime) deserialization failures

Type headers referencing producer packages

Solution

Custom JsonDeserializer

Ignore type headers

Explicit target class

JsonDeserializer<PatientDomainEvent> deserializer =
        new JsonDeserializer<>(PatientDomainEvent.class);

deserializer.addTrustedPackages("*");
deserializer.setUseTypeHeaders(false);

8. Why We Use Instant Instead of LocalDateTime
Problems with LocalDateTime

No timezone information

Serialized as arrays by Jackson

Requires extra Jackson modules

Error-prone in distributed systems

Why Instant is better

Timezone-safe (UTC)

Kafka/Jackson friendly

Industry standard for events

Analytics-ready

private Instant occurredAt;


Rule:

Events always use Instant

9. Kafka Listener Example
@KafkaListener(
  topics = "patient-events",
  groupId = "lab-load-service"
)
public void consume(PatientDomainEvent event) {
    labLoadService.handlePatientEvent(event);
}

10. Reset Consumer Offsets (Important)

When deserialization fails, Kafka retries the same message.

Reset offsets
docker exec -it kafka /opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --group lab-load-service \
  --reset-offsets \
  --to-latest \
  --execute \
  --topic patient-events

11. Derived Events (LAB_OVERLOAD)
Why derived events?

Patient Flow emits facts

Lab Load emits intelligence

Example derived event
{
  "eventType": "LAB_OVERLOAD",
  "currentLoad": 6,
  "maxCapacity": 5,
  "occurredAt": "2026-02-07T10:15:30Z",
  "sourceService": "lab-load-service"
}


Published to:

lab-events

12. Verifying Kafka Messages
Console consumer
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic lab-events \
  --from-beginning