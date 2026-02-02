# Data Architecture & Transaction Design

## Project Name
Advanced Hospital Flow & Lab Bottleneck Optimization System

---

## Overview

This document describes how data is stored, owned, and managed across microservices in the system.  
The design prioritizes **data ownership, scalability, fault isolation, and consistency without tight coupling**.

The system follows a **Database-per-Service** pattern and avoids distributed database transactions.

---

## Core Data Design Principles

1. Each microservice owns its data
2. No shared databases or schemas
3. No cross-service joins
4. Services communicate data changes via events
5. Eventual consistency is preferred over tight coupling

---

## Database-per-Service Strategy

Each microservice has its **own PostgreSQL database**.

| Service | Database Responsibility |
|------|--------------------------|
| Patient Flow Service | Patient journey timestamps |
| Lab Load Service | Lab capacity, queue length, processing time |
| Queue Optimization Service | Queue state and priority order |
| Alert Service | Alert history and status |
| Analytics Service | Aggregated metrics and KPIs |

---

## Why Not a Shared Database?

Using a shared database across microservices causes:
- Tight coupling between services
- Risky schema changes
- Difficult independent scaling
- High chance of cascading failures

This system avoids those problems by enforcing **strict data ownership**.

---

## High-Level Data Flow

Patient Event → Patient Flow DB
|
v
Event Broker (Kafka/RabbitMQ)
|
v
Lab Load DB → SLA / Bottleneck Detection
|
v
Analytics DB → KPI Reporting



---

## Service-wise Data Design

### Patient Flow Service (Write-heavy)

**Purpose**
- Track patient movement across hospital stages

**Key Data**
- Patient ID
- Visit ID
- Event type (REGISTERED, SAMPLE_COLLECTED, etc.)
- Timestamp

**Example Table**

patient_flow_event

id
patient_id
visit_id
event_type
event_time


**Design Notes**
- Append-only events
- No updates, only inserts
- Ideal for auditing and traceability

---

### Lab Load Service (Computation-heavy)

**Purpose**
- Detect bottlenecks and SLA risks

**Key Data**
- Lab ID
- Test type
- Current queue size
- Average processing time
- Capacity threshold

**Example Table**
lab_load_status

lab_id
test_type
queue_size
avg_processing_time
capacity_limit
last_updated


**Design Notes**
- Frequently updated
- Optimized with indexes
- Short transactions only

---

### Queue Optimization Service

**Purpose**
- Maintain dynamic priority queues

**Key Data**
- Sample ID
- Priority (EMERGENCY / IPD / OPD)
- Queue position
- Status

**Design Notes**
- Small tables
- Rule-based updates
- No historical storage

---

### Alert Service

**Purpose**
- Track alert lifecycle

**Key Data**
- Alert type (OVERLOAD / SLA_BREACH)
- Trigger time
- Status (OPEN / ACKNOWLEDGED / CLOSED)

**Design Notes**
- Alerts are event-driven
- Stored for audit and follow-up

---

### Analytics Service (Read-heavy)

**Purpose**
- Compute KPIs and trends

**Key Data**
- Daily aggregates
- Hourly load metrics
- SLA breach counts

**Design Notes**
- Derived data only
- Source of truth remains with owning services

---

## Transaction Design

### Local Transactions Only

Each service uses **local ACID transactions** within its own database.

✔ Fast  
✔ Reliable  
✔ Simple to reason about  

---

## Distributed Transactions (Avoided)

### Why 2-Phase Commit is NOT Used
- Poor performance
- Tight coupling
- Failure-prone in microservices

---

## Saga Pattern (Conceptual)

Cross-service workflows use **event-based Saga pattern**.

Example:
Sample Collected → Event Published
→ Lab Load Updated
→ Queue Reordered
→ Analytics Updated


If one step fails:
- Retry event
- Compensating logic if required
- No global rollback


## Eventual Consistency

The system accepts that:
- Data may not be immediately consistent across services
- Consistency is achieved over time via events

This trade-off enables:
- High availability
- Better scalability
- Fault tolerance

---

## Idempotency Handling

To handle duplicate events:
- Each event has a unique event ID
- Consumers track processed event IDs
- Duplicate events are ignored safely

---

## Deadlock Prevention Strategy

- Short-lived transactions
- Consistent update order
- Avoid long-running locks
- Retry with backoff for transient failures

(This is critical for high-volume lab systems.)

---

## SLA & Bottleneck Data Handling

### SLA Breach Detection
- Store start and end timestamps
- Compare actual duration with SLA threshold
- Emit SLA_BREACH event

### Bottleneck Detection
- Compare queue size vs capacity
- Monitor processing time trends
- Emit LAB_OVERLOAD event

---

## Data Security & Audit

- Sensitive identifiers masked where needed
- Role-based access enforced at service level
- Audit logs retained for compliance

---

## Summary

This data architecture ensures:
- Strong data ownership
- High scalability
- Minimal coupling
- Clear auditability
- Real-world readiness for hospital operations


