#  Distributed URL Shortener

> A production-inspired distributed URL shortening platform built with **Spring Boot, Kafka, Redis, PostgreSQL, and Spring Cloud**, focusing on scalability, resiliency, and real-world distributed systems engineering.

<p align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Driven-black?style=for-the-badge&logo=apachekafka)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=for-the-badge&logo=redis)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker)
![License](https://img.shields.io/badge/License-MIT-success?style=for-the-badge)

</p>

---

##  Project Vision

This project goes beyond building a simple URL shortener.

The goal is to explore how modern distributed backend systems are designed by implementing production-inspired architectural patterns such as asynchronous messaging, distributed caching, resilient event processing, CQRS-inspired analytics, Bloom Filters, and distributed rate limiting.

Instead of focusing solely on shortening URLs, the project emphasizes **performance, scalability, resilience, and observability**—the same engineering concerns encountered in real-world backend platforms.

---


##  Why This Project?

Most URL shortener tutorials focus on generating a short URL and redirecting users to the original destination. While that demonstrates the core functionality, it does not address the engineering challenges involved in building a system that can operate reliably under real-world traffic.

This project was created to explore how modern distributed backend systems are designed by implementing production-inspired architectural patterns rather than stopping at CRUD functionality.

The objective is to understand and implement concepts such as:

- Building independently deployable microservices
- Designing asynchronous event-driven workflows
- Reducing database load using Redis caching and Bloom Filters
- Protecting services with distributed rate limiting
- Processing analytics without impacting request latency
- Making Kafka consumers resilient using retries and Dead Letter Queues (DLQ)
- Measuring system performance through benchmarking and monitoring
- Preparing the platform for containerized and cloud-native deployments

Rather than treating each technology as an isolated feature, every component in this project exists to solve a specific engineering problem commonly encountered in production systems.


##  Project Objectives

The long-term goal of this project is to build a production-inspired distributed platform while learning the architectural patterns behind scalable backend systems.

Current objectives include:

- Design loosely coupled microservices
- Build asynchronous communication using Kafka
- Reduce latency using Redis caching
- Minimize unnecessary database queries with Bloom Filters
- Protect APIs using distributed rate limiting
- Build a scalable analytics pipeline
- Improve system resilience using Retry and Dead Letter Queues
- Benchmark system performance under concurrent workloads
- Monitor application health and metrics
- Deploy services using Docker and Kubernetes

---

#  System Architecture

The Distributed URL Shortener follows a microservices-based architecture where each service has a single responsibility and communicates through synchronous REST APIs and asynchronous Kafka events.

```text
                                 ┌─────────────────────────────┐
                                 │           Client            │
                                 └──────────────┬──────────────┘
                                                │
                                                ▼
                                 ┌─────────────────────────────┐
                                 │         API Gateway         │
                                 │-----------------------------│
                                 │ JWT Authentication          │
                                 │ Rate Limiting              │
                                 │ Request Routing            │
                                 └──────────────┬──────────────┘
                                                │
                        ┌───────────────────────┼────────────────────────┐
                        │                       │                        │
                        ▼                       ▼                        ▼
          ┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
          │    Auth Service     │  │     URL Service     │  │  Redirect Service   │
          └──────────┬──────────┘  └──────────┬──────────┘  └──────────┬──────────┘
                     │                        │                        │
                     │                        │                        │
                     ▼                        ▼                        ▼
              PostgreSQL               PostgreSQL                 Redis Cache
                                                                    │
                                                                    ▼
                                                            Bloom Filter Check
                                                                    │
                                                                    ▼
                                                              Publish Click Event
                                                                    │
                                                                    ▼
                                                             Apache Kafka
                                                                    │
                                                                    ▼
                                                         Analytics Service
                                                                    │
                                                                    ▼
                                                              PostgreSQL
```

---

## Architectural Highlights

- API Gateway centralizes authentication, routing, and request filtering.
- Services are independently deployable and loosely coupled.
- Kafka enables asynchronous event processing for analytics.
- Redis reduces redirect latency through caching.
- Bloom Filter minimizes unnecessary database lookups for invalid short URLs.
- Analytics processing is isolated from the redirect path using an event-driven architecture.
- Retry and Dead Letter Queue (DLQ) improve resilience during message processing failures.


#  Microservices

| Service | Responsibility |
|----------|----------------|
| API Gateway | Centralized routing, authentication, rate limiting, request filtering |
| Discovery Server | Service registration and discovery using Eureka |
| Auth Service | User registration, login, JWT authentication, refresh token management |
| URL Service | URL creation, management, ownership, and metadata |
| Redirect Service | Fast URL resolution, Redis caching, Bloom Filter validation, click event publishing |
| Analytics Service | Event consumption, click aggregation, browser/device analytics, dashboard APIs |
| Common Library | Shared DTOs, Kafka events, constants, utilities, and common models |



# Key Features & Engineering Decisions

The primary objective of this project is not simply to shorten URLs, but to implement production-inspired architectural patterns that improve scalability, reliability, and maintainability.

---

##  JWT Authentication & Authorization

Authentication is implemented using stateless JWT access tokens with refresh token rotation.

### Why?

- Eliminates server-side session storage.
- Enables horizontal scaling without sticky sessions.
- Provides secure authentication for distributed services.

---

##  API Gateway

All client requests pass through a centralized API Gateway.

### Responsibilities

- Request Routing
- JWT Validation
- Authentication
- Rate Limiting
- Cross-Origin Resource Sharing (CORS)
- Circuit Breaking

### Why?

Keeping cross-cutting concerns inside the gateway allows backend services to remain focused on business logic.

---

## ⚡ Redis URL Cache

Frequently accessed URLs are cached in Redis.

### Benefits

- Reduces database load.
- Improves redirect latency.
- Handles high read traffic efficiently.

---

##  Bloom Filter

Before querying the database, incoming short codes are validated using a Bloom Filter.

### Why?

Invalid short URLs are extremely common in public-facing URL shorteners.

Instead of querying PostgreSQL for every invalid request, the Bloom Filter filters out requests that definitely do not exist, significantly reducing unnecessary database traffic.

---

##  Event-Driven Analytics

The redirect service publishes click events asynchronously using Apache Kafka.

The Analytics Service consumes these events independently.

### Why?

Redirect latency should never depend on analytics processing.

By decoupling analytics from the request path, users receive fast redirects while analytics continue processing in the background.

---

##  CQRS-Inspired Analytics

Analytics processing is separated from request processing.

### Benefits

- Optimized write path.
- Independent analytics scaling.
- Reduced coupling between services.

---

## 🛡 Distributed Token Bucket Rate Limiter

A Redis-backed Token Bucket algorithm protects public endpoints from abuse.

### Implementation

- Redis
- Lua Scripts
- Atomic Operations
- Distributed Counters

### Why?

Unlike in-memory rate limiting, Redis ensures consistent enforcement across multiple gateway instances.

---

##  Kafka Retry & Dead Letter Queue (DLQ)

Message processing failures are handled using Spring Kafka's retry mechanism.

Workflow:

```text
Kafka
   │
   ▼
Consumer
   │
Exception
   │
Retry
   │
Retry
   │
Retry
   │
DLQ
```

### Benefits

- Prevents message loss.
- Handles transient failures automatically.
- Isolates permanently failed events.
- Improves overall system resilience.

---

##  Analytics Dashboard

Aggregated analytics are exposed through dedicated APIs.

Currently supported metrics include:

- Total Clicks
- Browser Distribution
- Device Distribution
- Operating System Distribution
- Last Click Timestamp

The dashboard is built using pre-aggregated analytics rather than scanning raw click events, improving query performance.

---

##  Performance Benchmarking

Performance testing is performed using **k6**, with metrics collected using **Prometheus** and visualized in **Grafana**.

Current benchmark highlights:

| Metric | Result |
|---------|--------|
| Maximum Concurrent Users Tested | 250 |
| Peak Throughput | ~176 Requests/sec |
| Error Rate | 0% |
| Stable Performance | Up to 100 Concurrent Users |

One of the benchmarks uncovered a concurrency issue involving duplicate refresh token generation.

The issue was resolved by introducing a unique JWT ID (`jti`) claim for every refresh token, eliminating duplicate token collisions under concurrent login requests.

This demonstrates the importance of load testing not only for measuring performance but also for identifying concurrency issues that functional testing may not expose.


#  Request Flow & Event Flow

This section illustrates how requests travel through the system and how individual services collaborate to provide a scalable, resilient, and low-latency URL shortening platform.

---

#  Authentication Flow

```text
                Client
                   │
                   ▼
            API Gateway
                   │
        JWT Authentication
                   │
                   ▼
            Auth Service
                   │
        Validate Credentials
                   │
                   ▼
             PostgreSQL
                   │
                   ▼
        Generate JWT Tokens
                   │
                   ▼
             Client
```

### Flow

1. User submits login credentials.
2. API Gateway forwards the request.
3. Auth Service validates the credentials.
4. Password is verified using BCrypt.
5. Access Token and Refresh Token are generated.
6. Refresh Token is stored securely.
7. JWT tokens are returned to the client.

---

# ✂ URL Creation Flow

```text
                Client
                   │
                   ▼
            API Gateway
                   │
             JWT Validation
                   │
                   ▼
             URL Service
                   │
        Generate Short Code
                   │
                   ▼
             PostgreSQL
                   │
                   ▼
        Publish URL Created Event
                   │
                   ▼
                Kafka
```

### Flow

1. Authenticated user submits a URL.
2. URL Service generates a unique short code.
3. Metadata is stored in PostgreSQL.
4. A `UrlCreatedEvent` is published to Kafka.
5. Other services can react asynchronously without coupling to URL Service.

---

#  Redirect Flow

```text
                  Client
                     │
                     ▼
              API Gateway
                     │
              Rate Limiter
                     │
                     ▼
            Redirect Service
                     │
             Bloom Filter Check
                     │
          ┌──────────┴──────────┐
          │                     │
       Invalid               Possible
          │                     │
          ▼                     ▼
      Return 404          Redis Cache
                                │
                         Cache Hit / Miss
                                │
                                ▼
                           PostgreSQL
                                │
                                ▼
                      Publish Click Event
                                │
                                ▼
                           Redirect User
```

### Why this design?

The redirect path is optimized for low latency.

Redis handles hot URLs.

Bloom Filter avoids unnecessary database lookups.

Analytics processing never blocks user redirects.

---

#  Analytics Processing Flow

```text
            Redirect Service
                   │
         Publish Click Event
                   │
                   ▼
                Kafka
                   │
                   ▼
          Analytics Consumer
                   │
        Parse User-Agent
                   │
                   ▼
      Aggregate Analytics
                   │
                   ▼
            PostgreSQL
```

### Analytics Collected

- Browser Distribution
- Operating System Distribution
- Device Distribution
- Total Click Count
- Last Click Timestamp

All analytics are processed asynchronously.

---

#  Kafka Retry & Dead Letter Queue

```text
                Kafka
                  │
                  ▼
        Analytics Consumer
                  │
           Exception ?
          ┌───────┴────────┐
          │                │
         No               Yes
          │                │
          ▼                ▼
      Save Event      Retry #1
                           │
                      Retry #2
                           │
                      Retry #3
                           │
                 Still Failing?
                     │       │
                    No      Yes
                     │       │
                     ▼       ▼
                Save Event  Dead Letter Queue
                                 │
                                 ▼
                     Dead Letter Consumer
```

### Benefits

- Automatic retry handling
- Fault isolation
- No event loss
- Improved reliability

---

#  Distributed Rate Limiting

```text
                Client
                   │
                   ▼
            API Gateway
                   │
         Endpoint Resolver
                   │
                   ▼
      Redis Token Bucket
                   │
          Tokens Available?
          ┌────────┴─────────┐
          │                  │
         Yes                No
          │                  │
          ▼                  ▼
   Forward Request      HTTP 429
```

The rate limiter is implemented using Redis and Lua scripts to ensure atomic token consumption across multiple gateway instances.

---

#  Bloom Filter Flow

```text
          Redirect Request
                 │
                 ▼
          Bloom Filter
          ┌────────┴─────────┐
          │                  │
      Definitely         Might Exist
      Not Present             │
          │                   ▼
          ▼              Redis Cache
      Return 404              │
                               ▼
                          PostgreSQL
```

The Bloom Filter prevents unnecessary database lookups for invalid short codes, reducing load on the database under high traffic.

---

# ⚡ Design Principles

The architecture follows several distributed system design principles:

- Single Responsibility per Service
- Event-Driven Communication
- Loose Coupling
- Asynchronous Processing
- Stateless Authentication
- Distributed Caching
- Fault Tolerance
- Horizontal Scalability
- Production-Inspired Architecture