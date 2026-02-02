# Microservice Boundaries & Responsibilities

## Overview

This document defines the microservices in the system and clearly outlines their responsibilities, data ownership, and interaction patterns.

Each service is designed around a single business capability and communicates with others primarily through events.

---

## Service List

1. API Gateway
2. Patient Flow Service
3. Lab Load Service
4. Queue Optimization Service
5. Alert Service
6. Analytics Service

---

## API Gateway

### Responsibilities
- Single entry point for clients
- Authentication and authorization
- Request routing

### Non-Responsibilities
- Business logic
- Data storage

---

## Patient Flow Service

### Responsibilities
- Track patient journey events
- Store timestamps
- Publish lifecycle events

### Non-Responsibilities
- Lab workload calculation
- Queue optimization
- Analytics

---

## Lab Load Service

### Responsibilities
- Monitor lab workload
- Detect bottlenecks
- Detect SLA risks
- Emit overload events

### Non-Responsibilities
- Queue reordering
- Alert notification
- Analytics storage

---

## Queue Optimization Service

### Responsibilities
- Maintain priority-based queues
- Reorder samples dynamically

### Non-Responsibilities
- Lab processing
- SLA calculation
- Alerting

---

## Alert Service

### Responsibilities
- Listen to critical events
- Generate operational alerts
- Track alert lifecycle

### Non-Responsibilities
- Business calculations
- Queue management
- Analytics

---

## Analytics Service

### Responsibilities
- Aggregate system events
- Compute KPIs
- Provide operational insights

### Non-Responsibilities
- Affect live workflows
- Trigger alerts
- Change queue state

---

## Design Benefits

- Clear ownership
- Independent scaling
- Reduced coupling
- Easier maintenance
- Interview-ready system design
