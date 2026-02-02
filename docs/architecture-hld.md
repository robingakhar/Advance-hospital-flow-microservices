# High-Level Architecture (HLD)

## Project Name
Advanced Hospital Flow & Lab Bottleneck Optimization System
------

## Architecture Overview

The system is designed using a **microservices-based, event-driven architecture** to address real-world hospital operational challenges such as patient waiting time, laboratory overload, and SLA breaches.

The architecture emphasizes:
- Loose coupling between services
- Independent scalability
- Fault isolation
- Real-time observability

This system acts as an **operational intelligence layer** on top of existing HMIS platforms.

---

## Architectural Style

### Microservices Architecture
Each business capability is implemented as an independent microservice with:
- Its own database
- Clear responsibility boundaries
- Independent deployment lifecycle

### Event-Driven Communication
Asynchronous events are used for:
- Patient flow tracking
- Lab load monitoring
- Alerting
- Analytics

This avoids tight coupling and enables real-time responsiveness.

---

## High-Level Component Diagram

Clients (UI / HMIS / Admin)
|
v
+--------------------+
| API Gateway |
+--------------------+
|
v
+--------------------+ +------------------+
| Patient Flow | -----> | Event Broker |
| Service | | (Kafka/RabbitMQ) |
+--------------------+ +------------------+
| |
v v
+--------------------+ +------------------+
| Lab Load Service | | Alert Service |
+--------------------+ +------------------+
|
v
+--------------------+
| Queue Optimization |
+--------------------+
|
v
+--------------------+
| Analytics Service |
+--------------------+



---

## Core Components

### API Gateway
**Responsibilities**
- Single entry point for all clients
- Authentication and authorization
- Request routing to downstream services
- Centralized security enforcement

**Design Rationale**
Centralizing cross-cutting concerns improves security, maintainability, and scalability.

---

### Patient Flow Service
**Responsibilities**
- Track patient journey timestamps across departments
- Capture events such as:
  - Registration completed
  - Sample collected
  - Test started
  - Report ready

**Design Rationale**
This service is write-heavy and event-focused, making it ideal as an event producer.

---

### Event Broker
**Responsibilities**
- Asynchronous event distribution
- Decoupling producers and consumers
- Supporting scalable event processing

**Key Events**
- SAMPLE_COLLECTED
- TEST_STARTED
- TEST_DELAYED
- LAB_OVERLOAD
- REPORT_READY
- SLA_BREACH

**Design Rationale**
Event-driven communication prevents cascading failures and supports real-time processing.

---

### Lab Load Service
**Responsibilities**
- Monitor laboratory workload in real time
- Track queue length and processing time
- Detect bottlenecks and SLA risks

**Design Rationale**
Laboratory processing is the primary bottleneck in hospital workflows and requires independent scaling and analysis.

---

### Queue Optimization Service
**Responsibilities**
- Reorder laboratory queues dynamically
- Apply priority-based processing:
  - Emergency
  - IPD
  - OPD

**Design Rationale**
Separating queue logic allows frequent rule changes without impacting core lab processing.

---

### Alert Service
**Responsibilities**
- Consume overload and SLA breach events
- Notify supervisors and operational staff
- Trigger corrective actions

**Design Rationale**
Alerts must be asynchronous and non-blocking to avoid impacting patient flow.

---

### Analytics Service
**Responsibilities**
- Aggregate events across services
- Compute KPIs such as:
  - Average waiting time
  - SLA breach frequency
  - Peak load duration

**Design Rationale**
Analytics is read-heavy and benefits from independent scaling and storage strategies.

---

## Data Architecture

### Database Strategy
- Each microservice owns its database
- No shared schemas or cross-service joins
- Data consistency is maintained within service boundaries

**Benefits**
- Loose coupling
- Independent schema evolution
- Improved fault isolation

---

## Communication Patterns

| Interaction Type | Pattern |
|------------------|---------|
| Client → Gateway | REST |
| Gateway → Services | REST |
| Service → Service (commands) | REST |
| System-wide updates | Events |
| Alerts and analytics | Asynchronous |

**Design Principle**
> REST for commands, Events for state changes

---

## Bottleneck & SLA Detection

### Bottleneck Detection
Performed in the **Lab Load Service** by evaluating:
- Incoming sample rate
- Processing capacity
- Queue backlog

### SLA Breach Detection
- Compare actual processing time against defined SLA thresholds
- Emit SLA_BREACH events for downstream consumers

---

## Scalability Strategy

| Component | Scaling Approach |
|---------|----------------|
| API Gateway | Horizontal |
| Patient Flow Service | Horizontal |
| Lab Load Service | Horizontal |
| Analytics Service | Horizontal |
| Event Broker | Partitioned |

Only high-load services are scaled to optimize resource usage.

---

## Fault Tolerance & Resilience

- Circuit breakers for synchronous calls
- Retry mechanisms with backoff for transient failures
- Dead Letter Queues for failed events
- Eventual consistency across services

---

## Security Considerations

- Centralized authentication at API Gateway
- Role-based access control
- Secure inter-service communication
- Audit logging for critical events

---

## Architectural Benefits

- Real-time operational visibility
- Reduced patient waiting time
- Proactive bottleneck detection
- Independent service scaling
- Strong alignment with real-world hospital workflows

---

## Summary

This high-level architecture enables a scalable, resilient, and observable hospital operations platform that solves real operational problems while demonstrating strong system design and microservices principles.
