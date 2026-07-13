# ADR-005: Event-Driven Architecture

| Field | Value |
|-------|-------|
| ADR ID | ADR-005 |
| Title | Event-Driven Architecture |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The Distributed URL Shortener platform consists of multiple independently deployable microservices.

Several business operations generate information that must be processed by multiple downstream services.

Examples include:

- URL Created
- URL Updated
- URL Deleted
- URL Redirected
- User Registered

These events are consumed by different services including:

- Analytics
- Notification
- Geo
- Audit
- Future Recommendation Engine

The architecture requires a communication model that allows services to evolve independently without introducing tight runtime dependencies.

---

# 2. Problem Statement

Determine how business events should be propagated between services.

The solution should:

- Decouple services
- Improve scalability
- Improve fault tolerance
- Support asynchronous processing
- Enable future feature expansion
- Avoid cascading failures

---

# 3. Decision Drivers

The following criteria were considered.

| Requirement | Priority |
|------------|----------|
| Loose Coupling | Critical |
| Scalability | Critical |
| Fault Isolation | Critical |
| High Throughput | High |
| Independent Deployment | High |
| Event Replay | High |
| Future Extensibility | High |

---

# 4. Alternatives Considered

---

## Option 1 — Direct REST Calls

Example

```
Redirect Service

↓

Analytics Service

↓

Notification Service

↓

Geo Service
```

Advantages

- Simple

Disadvantages

- Tight coupling
- Blocking communication
- Poor scalability
- Cascading failures

Decision

Rejected.

---

## Option 2 — Shared Database

Services poll a common database.

Advantages

- Easy implementation

Disadvantages

- Shared ownership
- Tight coupling
- Poor scalability
- Difficult maintenance

Decision

Rejected.

---

## Option 3 — Event-Driven Architecture using Kafka

Business events are published to Kafka.

Interested services consume the events independently.

Advantages

- Loose coupling
- Independent scaling
- Event replay
- High throughput
- Fault isolation
- Extensibility

Decision

Accepted.

---

# 5. Decision

The platform shall adopt an Event-Driven Architecture.

Business services publish domain events to Kafka.

Other services consume those events independently.

No service shall directly invoke another service for asynchronous business operations.

---

# 6. Event Flow

```
Client

↓

Redirect Service

↓

HTTP 302

↓

Publish URL_CLICKED Event

↓

Kafka

↓

Analytics Service

↓

Geo Service

↓

Notification Service

↓

Future Consumers
```

---

# 7. Event Ownership

Each business event has exactly one producer.

Multiple consumers are allowed.

| Event | Producer |
|--------|----------|
| USER_REGISTERED | Auth Service |
| URL_CREATED | URL Service |
| URL_UPDATED | URL Service |
| URL_DELETED | URL Service |
| URL_CLICKED | Redirect Service |
| URL_EXPIRED | URL Service |
| ANALYTICS_PROCESSED | Analytics Service |
| NOTIFICATION_SENT | Notification Service |

---

# 8. Event Naming Convention

Events shall follow the convention:

```
ENTITY_ACTION
```

Examples

```
USER_REGISTERED

URL_CREATED

URL_UPDATED

URL_DELETED

URL_CLICKED

URL_EXPIRED
```

This naming convention is:

- Predictable
- Easy to understand
- Language independent

---

# 9. Topic Naming Strategy

Kafka topics shall follow:

```
domain.event
```

Examples

```
user.registered

url.created

url.updated

url.deleted

url.clicked

url.expired

notification.sent
```

Future examples

```
billing.invoice.created

audit.login

fraud.detected
```

---

# 10. Event Structure

Every event shall contain standard metadata.

Example

```json
{
  "eventId": "...",
  "eventType": "URL_CREATED",
  "eventVersion": "1.0",
  "occurredAt": "...",
  "correlationId": "...",
  "producer": "url-service",
  "payload": {}
}
```

---

# 11. Event Versioning

Events shall support versioning.

Example

```
eventVersion

1.0

1.1

2.0
```

Consumers should remain backward compatible whenever possible.

---

# 12. Event Ordering

Ordering is guaranteed only within a Kafka partition.

The platform shall never assume global ordering.

Business logic must not depend on cross-partition ordering.

---

# 13. Event Idempotency

Consumers must assume duplicate event delivery.

Every consumer shall process events idempotently.

Processing the same event multiple times must produce the same result.

---

# 14. Event Retention

Kafka shall retain events for a configurable period.

Reasons

- Replay
- Recovery
- Debugging
- New Consumers

Retention duration will be defined during infrastructure configuration.

---

# 15. Failure Handling

If a consumer fails

↓

Kafka retains the event.

↓

Consumer retries.

↓

Successful processing resumes.

No event should be lost because of temporary failures.

---

# 16. Dead Letter Queue

Events that repeatedly fail processing shall be moved to a Dead Letter Queue.

Reasons include:

- Invalid payload
- Corrupt data
- Permanent business errors

This prevents poison messages from blocking consumers.

---

# 17. Retry Strategy

Transient failures shall use:

- Exponential Backoff
- Configurable Retry Count

Permanent failures shall be routed to the Dead Letter Queue.

---

# 18. Architecture Principles

The event-driven platform follows:

- Publish Events
- Do Not Share Databases
- Loose Coupling
- Independent Consumers
- Idempotent Processing
- Event Replay Support
- Failure Isolation

---

# 19. Consequences

## Positive

- Independent scaling
- Better resilience
- High throughput
- Easy extensibility
- New consumers can be added without modifying producers
- Better fault isolation

---

## Negative

- Eventual consistency
- Kafka operational complexity
- Consumer debugging
- Schema evolution challenges

These trade-offs are acceptable.

---

# 20. Future Evolution

Future platform enhancements may include:

- Event Sourcing
- CQRS
- Saga Pattern
- Schema Registry
- Multi-Region Kafka
- Stream Processing

without changing the event-driven philosophy.

---

# 21. Decision Summary

| Category | Decision |
|----------|----------|
| Communication | Event Driven |
| Event Broker | Apache Kafka |
| Topic Naming | domain.event |
| Event Naming | ENTITY_ACTION |
| Event Versioning | Required |
| Idempotency | Mandatory |
| DLQ | Enabled |
| Retry | Exponential Backoff |

---

# 22. References

- Enterprise Integration Patterns
- Designing Data-Intensive Applications
- Kafka: The Definitive Guide
- Confluent Event Design Best Practices