# Spring Boot Microservice Project Structure

## Overview

Each microservice in this system is implemented as an independent Spring Boot application following a layered architecture.

The structure enforces clear separation of concerns, improves maintainability, and supports independent deployment.

---

## Layered Architecture

The system follows a standard layered approach:

Controller → Service → Repository → Database

Additional layers are used for DTOs, events, configuration, and exception handling.

---

## Standard Package Structure

src/main/java/com/hospital/<service-name>/
├── controller/
├── service/
├── repository/
├── model/
├── dto/
├── event/
├── config/
├── exception/
└── <ServiceName>Application.java



---

## Package Responsibilities

### controller
Handles HTTP requests and responses. Contains no business logic.

### service
Contains business logic, transaction management, and event publishing.

### repository
Responsible for database access only.

### model
JPA entities mapped to database tables.

### dto
Request and response objects used for API communication.

### event
Domain event publishers and consumers.

### config
Spring and infrastructure configuration.

### exception
Centralized exception handling.

---

## Request Flow Example

1. Client sends HTTP request
2. Controller validates input
3. Service executes business logic
4. Repository persists data
5. Service publishes domain event
6. Response returned to client

---

## Design Benefits

- Clear separation of concerns
- Easier testing and debugging
- Loose coupling between layers
- Industry-standard structure
- Interview-ready explanation
