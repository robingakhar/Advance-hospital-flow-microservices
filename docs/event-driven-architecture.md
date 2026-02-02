# Event-Driven Architecture

## Overview

This system uses an event-driven architecture to enable loose coupling, scalability, and real-time responsiveness across microservices.

Services communicate state changes through immutable domain events rather than direct synchronous calls.

---

## Why Event-Driven Architecture

- Prevents cascading failures
- Enables independent scaling
- Improves system resilience
- Supports real-time analytics and alerts

---

## Core Components

### Event Producer
A service that publishes events when a business action occurs.

Example:
- Patient Flow Service publishes SAMPLE_COLLECTED event

---

### Event Broker
A messaging system (Kafka / RabbitMQ) that:
- Stores events
- Delivers them to consumers
- Decouples producers and consumers

---

### Event Consumer
A service that listens to events and reacts accordingly.

Examples:
- Lab Load Service
- Queue Optimization Service
- Analytics Service
- Alert Service

---

## Key Domain Events

| Event Name | Description |
|----------|-------------|
| SAMPLE_COLLECTED | Sample collected from patient |
| TEST_STARTED | Lab test processing started |
| TEST_DELAYED | Test processing delayed |
| REPORT_READY | Lab report generated |
| LAB_OVERLOAD | Lab capacity threshold exceeded |
| SLA_BREACH | SLA time exceeded |

---

## Event Structure

Each event follows a standard structure:

- eventId (unique identifier)
- eventType
- occurredAt (timestamp)
- sourceService
- payload (business data)

---

## Eventual Consistency

The system follows eventual consistency:
- Each service updates its own data independently
- Consistency is achieved over time via events
- No distributed transactions are used

---

## Failure Handling

- Events are retried on failure
- Duplicate events are handled via idempotency
- Dead Letter Queue used for unprocessable events

---

## Benefits

- Real-time bottleneck detection
- Scalable analytics
- Reliable alerting
- Clean microservices boundaries
