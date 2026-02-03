# API Design & Contracts

## Overview

This document defines the REST API contracts used by the microservices in the system. APIs are treated as stable contracts and are designed before implementation to ensure loose coupling and long-term maintainability.

---

## API Design Principles

- APIs represent business capabilities
- Use nouns, not verbs
- Version all APIs
- Do not expose database structures
- Prefer asynchronous processing where applicable

---

## HTTP Method Usage

| Method | Usage |
|------|------|
| GET | Read-only operations |
| POST | Create resources or submit commands |
| PUT | Full updates |
| PATCH | Partial updates |
| DELETE | Resource removal |

---

## Patient Flow Service APIs

### Create Patient Event

POST /api/v1/patient-events


Captures patient journey events and publishes corresponding domain events.

---

## Lab Load Service APIs

### Get Lab Load Status

GET /api/v1/labs/{labId}/load


Returns current lab workload and bottleneck status.

---

## Queue Optimization Service APIs

### Get Queue Status



GET /api/v1/queues/{labId}


Returns priority-based queue ordering for a lab.

---

## Alert Service APIs

### Get Alerts


GET /api/v1/alerts


### Acknowledge Alert


PATCH /api/v1/alerts/{alertId}/acknowledge


---

## Analytics Service APIs

### Get Daily KPIs


GET /api/v1/kpis/daily


---

## API Versioning

All APIs are versioned using URI-based versioning:


/api/v1/...


Breaking changes will be introduced in new versions only.

---

## REST vs Event Communication

- REST APIs are used for commands and queries
- Events are used for broadcasting state changes
- This separation ensures scalability and fault tolerance

---

## Summary

Well-designed APIs enable independent service evolution, reduce coupling, and provide a stable foundation for microservices communication.