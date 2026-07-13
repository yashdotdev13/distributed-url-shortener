# ADR-003A: Service Boundary Identification

| Field | Value |
|-------|-------|
| ADR ID | ADR-003A |
| Title | Service Boundary Identification |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The platform has adopted a Microservice Architecture (ADR-003).

The next architectural challenge is determining how the system should be decomposed into independent services.

Incorrect service boundaries lead to:

- Excessive service-to-service communication
- Tight coupling
- Shared ownership
- Distributed monoliths
- Difficult deployments
- Poor scalability

Service boundaries should therefore be driven by business capabilities rather than technical layers.

---

# 2. Problem Statement

Determine how the platform should be divided into independently deployable services.

The decomposition should maximize:

- Cohesion
- Loose Coupling
- Independent Deployment
- Independent Scaling
- Independent Ownership

while minimizing cross-service dependencies.

---

# 3. Design Principles

Every service shall satisfy the following principles.

## Single Business Responsibility

A service owns one business capability.

---

## High Cohesion

All functionality within a service should be closely related.

---

## Loose Coupling

Services should communicate through APIs or events.

They must never share implementation details.

---

## Independent Deployment

A service must be deployable without deploying other services.

---

## Independent Scaling

Each service should scale according to its own workload.

---

## Database Ownership

Each service owns its own database.

Other services must never access it directly.

---

## Event Ownership

Every business event has exactly one publisher.

Multiple consumers are allowed.

---

# 4. Candidate Business Capabilities

The platform contains the following business domains.

- Authentication
- URL Management
- URL Redirection
- Analytics
- Notifications
- Geolocation
- Administration

Each domain has different scalability characteristics.

---

# 5. Initial Service Decomposition

The platform shall initially consist of the following services.

```
                API Gateway
                     │
 ┌──────────────┬────┴────┬──────────────┐
 │              │         │              │
 │         Auth Service   │         URL Service
 │                        │
 │                        │
 │                  Redirect Service
 │                        │
 │                 Analytics Service
 │                        │
 │                 Notification Service
 │                        │
 │                    Geo Service
```

---

# 6. Service Responsibilities

---

## API Gateway

Responsibilities

- Authentication Delegation
- Routing
- Rate Limiting
- Request Logging
- Correlation IDs

Does NOT contain business logic.

---

## Authentication Service

Owns

- Users
- Roles
- JWT
- API Keys

Responsibilities

- Register
- Login
- Refresh Token
- Authorization

---

## URL Service

Owns

- Short URL Creation
- Metadata
- Alias Validation
- URL Ownership
- Expiration

It does NOT perform redirects.

---

## Redirect Service

Owns

- URL Lookup
- Redis Cache
- Redirect Logic
- HTTP 302 Responses

It does NOT update analytics.

Its only responsibility is:

Return the redirect as quickly as possible.

---

## Analytics Service

Owns

- Click Statistics
- Browser Analytics
- Country Analytics
- Device Analytics
- Dashboard Metrics

Consumes Kafka events.

Never blocks redirect requests.

---

## Notification Service

Owns

- Emails
- Expiration Notifications
- Future Webhooks

Consumes Kafka events.

---

## Geo Service

Owns

- IP Resolution
- Country Detection
- Region Detection
- Geo Metadata

Consumes click events.

---

# 7. Why Redirect Is Separate

Redirect traffic is dramatically higher than URL creation traffic.

Estimated workload:

```
Create URL

≈ 6 RPS

-------------------

Redirect

≈ 600+ RPS
```

These workloads require different scaling strategies.

Separating Redirect Service allows:

- Independent scaling
- Independent caching
- Lower latency
- Failure isolation

---

# 8. Why Analytics Is Separate

Analytics processing is not part of the critical request path.

Processing analytics synchronously would increase redirect latency.

Instead:

```
Redirect

↓

Kafka Event

↓

Analytics Service

↓

Analytics Database
```

This keeps redirects fast.

---

# 9. Why Authentication Is Separate

Authentication is a cross-cutting concern.

It is shared by:

- Dashboard
- URL APIs
- Admin APIs
- Future Mobile Apps
- Third-Party Developers

Separating authentication simplifies reuse.

---

# 10. Why Notification Is Separate

Notification workloads are asynchronous.

Examples

- Email
- URL Expiration
- Security Alerts

These should never delay user-facing requests.

---

# 11. Why Geo Service Is Separate

Geolocation introduces:

- External databases
- Periodic data updates
- IP lookup logic

Keeping it isolated prevents unrelated services from becoming dependent on GeoIP libraries.

---

# 12. Data Ownership

| Service | Owns |
|----------|------|
| Auth Service | Users, Roles, API Keys |
| URL Service | URLs, Metadata |
| Redirect Service | Redis Cache |
| Analytics Service | Click Analytics |
| Notification Service | Notification Records |
| Geo Service | Geo Metadata |

Each service exclusively owns its data.

---

# 13. Event Ownership

| Event | Publisher |
|--------|-----------|
| User Registered | Auth Service |
| URL Created | URL Service |
| URL Updated | URL Service |
| URL Deleted | URL Service |
| URL Clicked | Redirect Service |
| Analytics Processed | Analytics Service |
| Notification Sent | Notification Service |

Each event has a single publisher.

---

# 14. Anti-Patterns

The platform shall avoid:

### Shared Database

Multiple services accessing the same database.

---

### Shared Business Logic

Copying business logic between services.

---

### Synchronous Chains

```
Gateway

↓

Service A

↓

Service B

↓

Service C

↓

Service D
```

Long synchronous call chains reduce availability.

---

### Distributed Monolith

Services that cannot be deployed independently.

---

# 15. Consequences

## Positive

- Better scalability
- Better ownership
- Easier deployments
- Independent evolution
- Better fault isolation
- Lower coupling

---

## Negative

- More services
- More infrastructure
- Distributed communication
- Eventual consistency
- Operational complexity

These trade-offs are acceptable.

---

# 16. Architecture Principles

The platform shall follow:

- Business Capability First
- Database Per Service
- Event Driven Communication
- Stateless Services
- Independent Scaling
- Independent Deployment
- High Cohesion
- Loose Coupling

---

# 17. Decision Summary

| Category | Decision |
|----------|----------|
| Architecture Style | Microservices |
| Boundary Strategy | Business Capability |
| Deployment | Independent |
| Database | One Database per Service |
| Communication | REST + Kafka |
| Ownership | Independent |
| Scaling | Per Service |

---

# 18. References

- Domain-Driven Design (Eric Evans)
- Building Microservices (Sam Newman)
- Software Architecture: The Hard Parts
- Designing Data-Intensive Applications