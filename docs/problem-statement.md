# Problem Statement

## Project Title
Advanced Hospital Flow & Lab Bottleneck Optimization System

---

## Background

Hospitals handle a high volume of patients daily, especially during peak OPD hours.  
While most hospitals use an HMIS for registration, billing, and reports, they lack **real-time operational visibility** into patient movement and laboratory workload.

As a result:
- Patients experience long and unpredictable waiting times
- Laboratories face sudden overload without advance warning
- Doctors and supervisors lack live status of lab processing
- Management has no data-driven insight into bottlenecks

This problem directly impacts patient satisfaction, staff efficiency, and overall hospital throughput.

---

## Problem Definition

The core problem is the **absence of a real-time, event-driven system** that can:

1. Track patient journey timestamps across departments
2. Monitor laboratory workload dynamically
3. Detect and predict bottlenecks
4. Trigger actionable alerts before SLA violations occur
5. Provide analytics for operational decision-making

Existing systems are largely **transactional**, not **observational or predictive**.

---

## Objectives

The objective of this project is to design and build a **scalable microservices-based system** that:

- Captures real-time patient flow events
- Identifies laboratory overload conditions automatically
- Optimizes test queues based on priority
- Sends alerts to supervisors and staff
- Generates analytics for continuous improvement

This system is intended as an **operational intelligence layer**, not a replacement for existing HMIS systems.

---

## In-Scope Features

- Patient journey tracking (registration → lab → report)
- Laboratory load monitoring
- Priority-based queue optimization
- Event-driven alerts and notifications
- Analytics and KPI reporting
- Secure, scalable microservices architecture

---

## Out-of-Scope Features

To maintain focus and clarity, the following are explicitly out of scope:

- Billing and payments
- Pharmacy management
- Electronic Medical Records (EMR)
- Insurance and claims processing

---

## Key Performance Indicators (KPIs)

The system will measure and expose the following KPIs:

- Average patient waiting time per stage
- Laboratory processing time per test category
- SLA breach frequency
- Peak load duration
- Queue delay by priority (Emergency / OPD / IPD)

---

## Target Users

- Laboratory technicians
- Doctors
- Hospital supervisors
- Operations and quality management teams

---

## Non-Functional Requirements

- High availability
- Horizontal scalability
- Fault tolerance
- Eventual consistency across services
- Secure access and auditability
- Observability (logs, metrics, traceability)

---

## Success Criteria

The system is considered successful if it can:

- Detect lab overload in real time
- Reduce average waiting time through queue optimization
- Provide clear operational visibility via metrics and events
- Scale independently per microservice
- Be explainable and defensible in system design interviews
