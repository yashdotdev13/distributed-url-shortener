# Non-Functional Requirements

## Document Information

| Field | Value |
|-------|-------|
| Document | Non-Functional Requirements |
| Version | 1.0 |
| Status | Draft |
| Owner | Yash Chauhan |

---

# 1. Introduction

This document defines the quality attributes and operational characteristics of the Distributed URL Shortener platform.

Unlike functional requirements, these requirements describe **how the system should behave** under various operating conditions.

These requirements directly influence architecture, infrastructure, scalability, and technology choices.

---

# 2. Availability

## NFR-001

The platform shall maintain an uptime of at least **99.9%**.

### Rationale

Users should be able to access shortened URLs at all times.

---

## NFR-002

Redirect functionality shall remain available even if analytics processing becomes unavailable.

### Rationale

Redirecting users is the core business functionality.

Analytics should never block redirects.

---

# 3. Performance

## NFR-003

The redirect endpoint should respond within:

- P50 < 10 ms
- P95 < 50 ms
- P99 < 100 ms

under normal operating conditions.

---

## NFR-004

URL creation requests should complete within:

- P95 < 200 ms

---

## NFR-005

The platform shall support asynchronous analytics processing.

Analytics must not increase redirect latency.

---

# 4. Scalability

## NFR-006

Application services shall scale horizontally.

The system should support adding more service instances without application changes.

---

## NFR-007

Caching should reduce database read load for frequently accessed URLs.

---

## NFR-008

The architecture shall support future database sharding.

---

# 5. Reliability

## NFR-009

Transient failures shall be retried using exponential backoff.

---

## NFR-010

Failed asynchronous messages shall be moved to a Dead Letter Queue.

---

## NFR-011

Business events shall not be lost.

Reliable delivery must be ensured.

---

# 6. Security

## NFR-012

All APIs except public redirects shall require authentication.

---

## NFR-013

Passwords shall never be stored in plaintext.

---

## NFR-014

Sensitive configuration shall not be stored inside source code.

---

## NFR-015

API requests shall be protected against abuse using rate limiting.

---

# 7. Maintainability

## NFR-016

Services shall be independently deployable.

---

## NFR-017

Every service shall own its own database.

---

## NFR-018

Source code shall follow clean architecture principles.

---

# 8. Observability

## NFR-019

Every service shall expose health endpoints.

---

## NFR-020

The platform shall expose operational metrics.

Examples:

- Request Count
- Error Rate
- Latency
- JVM Metrics
- Kafka Consumer Lag

---

## NFR-021

Distributed tracing shall be supported.

Every request should be traceable across services.

---

## NFR-022

Centralized logging shall be implemented.

Logs shall be searchable.

---

# 9. Fault Tolerance

## NFR-023

Failure of one service shall not cascade to unrelated services.

---

## NFR-024

Circuit breakers shall protect downstream services.

---

## NFR-025

The system shall degrade gracefully whenever possible.

---

# 10. Deployment

## NFR-026

The platform shall be containerized.

---

## NFR-027

The platform shall be deployable on Kubernetes.

---

## NFR-028

Rolling deployments shall be supported without downtime.

---

# 11. Monitoring

## NFR-029

Operational dashboards shall display:

- CPU
- Memory
- Request Rate
- Response Time
- Kafka Lag
- Cache Hit Ratio

---

## NFR-030

Alerts shall be generated for critical failures.

Examples:

- Service Down
- High Error Rate
- Kafka Consumer Failure
- Redis Failure

---

# 12. Disaster Recovery

## NFR-031

Databases shall support automated backup.

---

## NFR-032

Recovery procedures shall be documented.

---

# 13. Future Scalability

The architecture should support future enhancements including:

- Multi-region deployment
- CDN Integration
- Global Load Balancing
- Read Replicas
- Distributed Database Sharding

without requiring significant architectural changes.

---

# 14. Requirement Summary

| ID | Category |
|----|----------|
| NFR-001 | Availability |
| NFR-002 | Availability |
| NFR-003 | Performance |
| NFR-004 | Performance |
| NFR-005 | Performance |
| NFR-006 | Scalability |
| NFR-007 | Scalability |
| NFR-008 | Scalability |
| NFR-009 | Reliability |
| NFR-010 | Reliability |
| NFR-011 | Reliability |
| NFR-012 | Security |
| NFR-013 | Security |
| NFR-014 | Security |
| NFR-015 | Security |
| NFR-016 | Maintainability |
| NFR-017 | Maintainability |
| NFR-018 | Maintainability |
| NFR-019 | Observability |
| NFR-020 | Observability |
| NFR-021 | Observability |
| NFR-022 | Observability |
| NFR-023 | Fault Tolerance |
| NFR-024 | Fault Tolerance |
| NFR-025 | Fault Tolerance |
| NFR-026 | Deployment |
| NFR-027 | Deployment |
| NFR-028 | Deployment |
| NFR-029 | Monitoring |
| NFR-030 | Monitoring |
| NFR-031 | Disaster Recovery |
| NFR-032 | Disaster Recovery |