# ADR-003: Microservice Architecture

| Field | Value |
|-------|-------|
| ADR ID | ADR-003 |
| Title | Microservice Architecture |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The Distributed URL Shortener platform is intended to demonstrate production-grade backend engineering practices.

The platform is expected to evolve from a relatively small deployment into a scalable distributed system capable of supporting millions of users and billions of redirect requests.

The architecture must support:

- Independent scalability
- High availability
- Fault isolation
- Cloud-native deployment
- Event-driven communication
- Continuous delivery
- Independent service evolution

Choosing the correct architectural style is one of the most important design decisions because changing architectural patterns after implementation is extremely expensive.

---

# 2. Problem Statement

Determine the most appropriate architectural style for the platform.

Possible architectural styles include:

- Monolithic Architecture
- Modular Monolith
- Microservices

The selected architecture should balance:

- Simplicity
- Maintainability
- Scalability
- Operational Complexity
- Team Productivity
- Future Growth

---

# 3. Decision Drivers

The following criteria were considered.

| Requirement | Priority |
|-------------|----------|
| Independent Deployment | Critical |
| Horizontal Scaling | Critical |
| Fault Isolation | Critical |
| Loose Coupling | High |
| Event-Driven Communication | High |
| Cloud Native Deployment | High |
| Maintainability | High |
| Independent Development | Medium |

---

# 4. Alternatives Considered

---

## Option 1 — Monolithic Architecture

### Description

All business functionality exists inside a single deployable application.

Example

```
Authentication

↓

URL Management

↓

Analytics

↓

Notifications

↓

Admin

↓

Single Spring Boot Application
```

### Advantages

- Simple
- Easy debugging
- Single deployment
- Low operational overhead
- Easy local development

### Disadvantages

- Entire application must be redeployed
- Difficult to scale individual modules
- Tight coupling
- Poor fault isolation
- Technology lock-in

---

## Option 2 — Modular Monolith

### Description

A single deployable application containing well-separated internal modules.

### Advantages

- Easier migration to microservices
- Better code organization
- Simple deployment
- Lower operational complexity

### Disadvantages

- Single deployment unit
- Limited independent scaling
- Shared runtime

---

## Option 3 — Microservices

### Description

Business capabilities are implemented as independently deployable services.

Each service owns:

- Business Logic
- Database
- Deployment
- Lifecycle

### Advantages

- Independent deployment
- Independent scaling
- Fault isolation
- Better maintainability
- Technology independence
- Better team ownership
- Cloud-native architecture

### Disadvantages

- Distributed systems complexity
- Network communication
- Eventual consistency
- Service discovery
- Distributed tracing
- Operational overhead

---

# 5. Evaluation

| Architecture | Scalability | Complexity | Cloud Native | Future Growth |
|--------------|-------------|------------|---------------|---------------|
| Monolith | Low | Low | Medium | Low |
| Modular Monolith | Medium | Medium | Medium | High |
| Microservices | High | High | Excellent | Excellent |

---

# 6. Decision

The Distributed URL Shortener platform shall adopt a **Microservice Architecture**.

Each business capability will be implemented as an independent service.

---

# 7. Initial Service Boundaries

The platform will initially consist of the following services.

```
API Gateway

↓

Authentication Service

↓

URL Service

↓

Redirect Service

↓

Analytics Service

↓

Notification Service

↓

Geo Service
```

Each service has a single business responsibility.

---

# 8. Service Ownership

Each microservice owns:

- Business Logic
- Database
- REST APIs
- Kafka Events
- Docker Image
- Kubernetes Deployment
- Monitoring
- Health Checks

No service may directly access another service's database.

---

# 9. Communication Strategy

The platform shall use two communication styles.

## Synchronous

REST APIs

Used for:

- Authentication
- URL Creation
- User Operations

---

## Asynchronous

Kafka

Used for:

- Analytics
- Notifications
- Audit Logs
- Background Processing

---

# 10. Why Not a Monolith?

Although a monolith would simplify the initial implementation, it introduces several long-term limitations.

Examples include:

- Entire application redeployment
- Shared scaling
- Tight coupling
- Reduced fault isolation

These limitations conflict with the project's objectives of demonstrating distributed systems engineering.

---

# 11. Why Not a Modular Monolith?

A modular monolith is a valid architectural approach and is often recommended for many production systems.

However, the primary goal of this project is educational.

The project aims to demonstrate:

- Kubernetes
- Kafka
- Distributed Tracing
- Redis
- Independent Deployments
- Event-Driven Architecture

These capabilities are better demonstrated using microservices.

---

# 12. Design Principles

Every service shall follow:

- Single Responsibility Principle
- Database per Service
- Stateless Deployment
- Independent Deployment
- Independent Scaling
- Loose Coupling
- High Cohesion

---

# 13. Risks

Microservices introduce several challenges.

Examples include:

- Distributed Transactions
- Service Discovery
- Network Latency
- Operational Complexity
- Eventual Consistency
- Debugging Complexity

These risks will be mitigated using:

- Kafka
- OpenTelemetry
- Jaeger
- Prometheus
- Health Checks
- Circuit Breakers
- Retry Policies

---

# 14. Future Evolution

The architecture shall support:

- Additional Services
- Read Replicas
- Multi-Region Deployment
- Service Mesh
- gRPC Communication
- Event Sourcing
- CQRS

without significant redesign.

---

# 15. Consequences

## Positive

- Independent deployments
- Better scalability
- Fault isolation
- Better observability
- Easier future expansion
- Better ownership boundaries

---

## Negative

- Increased infrastructure
- Higher operational complexity
- More monitoring required
- More deployment pipelines
- Distributed debugging

These trade-offs are acceptable given the project's objectives.

---

# 16. Architecture Principles

The platform shall follow the following principles.

- Business capability over technical layers
- Database per service
- Event-driven communication
- Stateless services
- Cloud-native deployment
- Independent scalability
- Failure isolation
- Observability by default

---

# 17. Decision Summary

| Category | Decision |
|----------|----------|
| Architecture | Microservices |
| Deployment | Independent |
| Communication | REST + Kafka |
| Database | Database per Service |
| Scaling | Horizontal |
| Infrastructure | Kubernetes |
| Observability | Built-in |

---

# 18. References

- Building Microservices (Sam Newman)
- Designing Data-Intensive Applications (Martin Kleppmann)
- Domain-Driven Design (Eric Evans)
- Kubernetes Documentation
- AWS Microservices Whitepaper