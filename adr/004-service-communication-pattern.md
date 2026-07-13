# ADR-004: Service Communication Pattern

| Field | Value |
|-------|-------|
| ADR ID | ADR-004 |
| Title | Service Communication Pattern |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The Distributed URL Shortener platform consists of multiple independently deployable microservices.

These services must exchange information to fulfill business operations.

Examples include:

- User Authentication
- URL Creation
- URL Redirection
- Analytics Processing
- Notification Delivery
- Geolocation Processing

Selecting an appropriate communication strategy is essential because it directly affects latency, scalability, reliability, fault tolerance, and operational complexity.

---

# 2. Problem Statement

Determine how microservices should communicate.

Possible communication mechanisms include:

- Synchronous REST APIs
- gRPC
- Asynchronous Messaging (Kafka)
- Hybrid Communication

The chosen solution should minimize coupling while maximizing system reliability and scalability.

---

# 3. Decision Drivers

The following criteria were considered.

| Requirement | Priority |
|-------------|----------|
| Low Latency | Critical |
| Fault Isolation | Critical |
| Loose Coupling | Critical |
| Scalability | High |
| Reliability | High |
| Simplicity | High |
| Eventual Consistency Support | High |
| Cloud Native Compatibility | High |

---

# 4. Alternatives Considered

---

## Option 1 — REST Only

### Advantages

- Simple
- Easy debugging
- Familiar
- Mature tooling

### Disadvantages

- Tight runtime coupling
- Cascading failures
- Blocking communication
- Increased latency
- Poor resilience

Decision

Rejected.

---

## Option 2 — Kafka Only

### Advantages

- Loose coupling
- High scalability
- Excellent reliability

### Disadvantages

- Cannot provide immediate responses
- Complex request-response workflows
- Eventual consistency everywhere
- Higher operational complexity

Decision

Rejected.

---

## Option 3 — gRPC Only

### Advantages

- High performance
- Efficient serialization
- Streaming support

### Disadvantages

- Browser incompatibility
- Increased complexity
- Additional gateway required

Decision

Deferred.

---

## Option 4 — Hybrid Communication

REST for synchronous business operations.

Kafka for asynchronous event processing.

Decision

Accepted.

---

# 5. Decision

The platform shall adopt a hybrid communication model.

## Synchronous Communication

REST APIs

Used when:

- Immediate response is required
- Client waits for result
- Business operation is transactional

Examples:

- Login
- Register
- Create URL
- Update URL
- Delete URL
- Fetch Dashboard

---

## Asynchronous Communication

Apache Kafka

Used when:

- Caller does not need immediate response
- Background processing
- Event propagation
- Independent scalability

Examples:

- Analytics
- Notifications
- Audit Logs
- Future Recommendation Engine

---

# 6. Communication Matrix

| Source | Destination | Method |
|---------|-------------|--------|
| Client | API Gateway | REST |
| API Gateway | Auth Service | REST |
| API Gateway | URL Service | REST |
| Client | Redirect Service | REST |
| Redirect Service | Kafka | Event |
| Kafka | Analytics Service | Event |
| Kafka | Notification Service | Event |
| Kafka | Geo Service | Event |

---

# 7. Why Not Everything REST?

Consider a redirect request.

```
Client

↓

Redirect Service

↓

Analytics Service

↓

Geo Service

↓

Notification Service

↓

302 Redirect
```

The user waits for every service.

Problems:

- High latency
- Cascading failures
- Lower availability

This violates our non-functional requirements.

---

# 8. Why Kafka for Analytics?

Analytics is not required before redirecting the user.

Instead:

```
Client

↓

Redirect Service

↓

HTTP 302

↓

Kafka

↓

Analytics Service
```

Advantages:

- Lower latency
- Independent scaling
- Fault isolation
- Better throughput

---

# 9. Failure Isolation

A failure in one service should not affect unrelated services.

Example:

Analytics Service Down

↓

Redirect Service

↓

Still Returns HTTP 302

↓

Analytics Event Waits in Kafka
```

The platform continues serving users.

---

# 10. Eventual Consistency

Some business operations do not require immediate consistency.

Examples:

- Analytics
- Notifications
- Audit Logs

These operations are allowed to become consistent shortly after the primary business action.

---

# 11. Coupling Analysis

REST introduces temporal coupling.

The caller waits until the receiver completes processing.

Kafka removes temporal coupling.

The publisher continues immediately after producing an event.

This improves overall system resilience.

---

# 12. Architecture Principles

The platform follows the following communication principles.

- Request-Response uses REST.
- Event Propagation uses Kafka.
- Business APIs remain synchronous.
- Background work remains asynchronous.
- Avoid long synchronous service chains.
- Prefer event-driven communication whenever immediate consistency is unnecessary.

---

# 13. Consequences

## Positive

- Lower latency
- Better scalability
- Independent deployments
- Better fault isolation
- Higher availability
- Better throughput

---

## Negative

- Eventual consistency
- Message ordering considerations
- Additional infrastructure
- Kafka operational complexity

These trade-offs are acceptable.

---

# 14. Future Evolution

Future versions may introduce:

- gRPC for internal communication
- Service Mesh
- Event Sourcing
- CQRS
- Saga Pattern

without changing the overall communication philosophy.

---

# 15. Decision Summary

| Category | Decision |
|----------|----------|
| External Communication | REST |
| Internal Request-Response | REST |
| Event Communication | Kafka |
| Communication Style | Hybrid |
| Consistency | Strong + Eventual |
| Fault Isolation | Enabled |
| Scalability | High |

---

# 16. References

- Building Microservices – Sam Newman
- Designing Data-Intensive Applications – Martin Kleppmann
- Enterprise Integration Patterns
- Kafka Documentation
- AWS Event-Driven Architecture Whitepaper