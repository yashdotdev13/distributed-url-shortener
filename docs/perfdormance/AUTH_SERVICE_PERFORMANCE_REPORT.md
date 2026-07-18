# Auth Service Performance Benchmark Report

> **Project:** Distributed URL Shortener  
> **Service:** Auth Service  
> **Version:** v1.0  
> **Environment:** Local Development  
> **Date:** July 2026

---

# Table of Contents

- Overview
- Service Responsibilities
- Technology Stack
- Architecture
- Test Environment
- Monitoring Stack
- Benchmark Methodology
- Performance Results
- Issue Discovered During Load Testing
- Root Cause Analysis
- Fix Applied
- System Behavior Analysis
- Performance Bottlenecks
- Production Recommendations
- Conclusion

---

# Overview

The Authentication Service is responsible for user authentication and authorization within the Distributed URL Shortener system.

It provides secure authentication using JWT tokens and persistent refresh tokens while integrating with PostgreSQL and Eureka Discovery Server.

The objective of this benchmark was to evaluate:

- Login performance
- System stability
- Concurrent request handling
- CPU utilization
- Scalability
- Production readiness

---

# Service Responsibilities

The Auth Service provides:

- User Registration
- User Login
- JWT Access Token Generation
- Refresh Token Generation
- Refresh Token Rotation
- Logout
- Current User Endpoint
- Role Based Authentication

---

# Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security |
| Authentication | JWT |
| Password Hashing | BCrypt |
| Database | PostgreSQL |
| Migration | Flyway |
| Service Discovery | Eureka |
| Monitoring | Spring Boot Actuator |
| Metrics | Micrometer |
| Time Series Database | Prometheus |
| Dashboard | Grafana |
| Load Testing | k6 |

---

# Architecture

```
                    +----------------+
                    |      k6        |
                    +--------+-------+
                             |
                             |
                     HTTP Login Requests
                             |
                             ▼
                +---------------------------+
                |       Auth Service        |
                +---------------------------+
                | Spring Security           |
                | JWT                       |
                | BCrypt                    |
                | Refresh Tokens            |
                +------------+--------------+
                             |
                             |
                      PostgreSQL Database
```

---

# Test Environment

## Application

- Spring Boot Application
- Stateless Authentication
- JWT Access Tokens
- JWT Refresh Tokens
- BCrypt Password Verification

## Database

- PostgreSQL
- Local Instance

## Monitoring

- Prometheus
- Grafana

Collected Metrics

- CPU Usage
- HTTP Requests
- JVM Metrics
- Heap Memory
- Threads

---

# Benchmark Methodology

Tool Used

```
k6
```

Endpoint Tested

```
POST /api/v1/auth/login
```

Each login request performs the following operations:

1. Receive Login Request
2. Validate Credentials
3. Fetch User From Database
4. BCrypt Password Verification
5. Generate Access Token
6. Generate Refresh Token
7. Persist Refresh Token
8. Return Authentication Response

---

# Performance Results

## 10 Virtual Users

| Metric | Value |
|--------|------:|
| Throughput | 8.73 req/s |
| Average Latency | 103 ms |
| P95 | 104 ms |
| Error Rate | 0% |

---

## 50 Virtual Users

| Metric | Value |
|--------|------:|
| Throughput | 42.7 req/s |
| Average Latency | 80 ms |
| P95 | 93 ms |
| Error Rate | 0% |

---

## 100 Virtual Users

| Metric | Value |
|--------|------:|
| Throughput | 85.1 req/s |
| Average Latency | 83 ms |
| P95 | 100 ms |
| Error Rate | 0% |

The service demonstrated excellent scalability up to 100 concurrent users.

---

## 250 Virtual Users (Continuous Load)

| Metric | Value |
|--------|------:|
| Throughput | 175.9 req/s |
| Average Latency | 1.39 s |
| Median Latency | 1.14 s |
| P95 | 4.15 s |
| Maximum Latency | 11.58 s |
| Error Rate | 0% |

---

# Issue Discovered During Load Testing

During the initial concurrent benchmark, the application started returning HTTP 500 responses.

Database Error

```
refresh_tokens_token_key
```

This occurred only under concurrent login requests.

---

# Root Cause Analysis

The refresh token JWT contained only:

- Subject
- Issued At
- Expiration Time

Multiple concurrent requests generated identical refresh tokens within the same timestamp.

Since the database enforces a UNIQUE constraint on the refresh token, duplicate inserts caused transaction failures.

---

# Fix Applied

A unique JWT ID (`jti`) claim was added to every generated token.

```java
.id(UUID.randomUUID().toString())
```

Result

- Unique refresh tokens
- No duplicate token collisions
- Zero HTTP 500 errors during concurrent testing

---

# Monitoring Results

Monitoring was performed using:

- Prometheus
- Grafana

The following metrics were continuously observed:

- CPU Usage
- JVM Heap
- Thread Count
- HTTP Request Rate

---

# CPU Analysis

Grafana reported CPU utilization approaching **90%** during the 250 Virtual User benchmark.

This indicates that the application became **CPU-bound** rather than memory-bound.

The major contributors to CPU usage are:

- BCrypt Password Verification
- JWT Generation
- Request Processing

Since BCrypt is intentionally computationally expensive, this behavior is expected for authentication workloads.

---

# System Behavior

### Up to 100 Virtual Users

- Stable response times
- Excellent throughput
- Zero request failures
- Healthy resource utilization

---

### At 250 Virtual Users

The application entered resource saturation.

Observed behavior:

- Increased response latency
- Higher request queuing
- Stable throughput
- Zero request failures

Importantly, the service continued processing all requests successfully without returning server errors.

This demonstrates graceful degradation under sustained load.

---

# Performance Bottlenecks

Based on benchmark results and runtime metrics, the primary bottlenecks are:

## CPU

BCrypt password hashing is CPU intensive and dominates authentication workloads.

---

## Database Connection Pool

The default HikariCP configuration may limit database concurrency under heavy load.

Recommended configuration:

```properties
spring.datasource.hikari.maximum-pool-size=30
spring.datasource.hikari.minimum-idle=10
```

---

## Tomcat Thread Pool

Recommended configuration:

```properties
server.tomcat.threads.max=300
server.tomcat.accept-count=200
```

---

# Security Considerations

The benchmark intentionally retained the default BCrypt strength.

Reducing BCrypt rounds would improve benchmark numbers but weaken password security.

The benchmark prioritizes realistic production behavior over synthetic performance improvements.

---

# Key Achievements

 Stateless JWT Authentication

 Secure BCrypt Password Verification

 Refresh Token Rotation

 Flyway Database Migrations

 Spring Security

 Prometheus Monitoring

 Grafana Dashboards

 k6 Performance Testing

 Concurrency Bug Identification

 Root Cause Analysis

 Production-Level Bug Fix

 Zero Request Failures Under Load

---

# Future Improvements

- API Gateway Integration
- Redis Token Caching
- Kubernetes Deployment
- Horizontal Scaling
- Distributed Tracing
- Grafana Alerting
- CI/CD Performance Regression Tests
- Autoscaling using Kubernetes HPA

---

# Conclusion

The Auth Service demonstrated strong stability and reliability under concurrent authentication workloads.

The service maintained **0% request failures** across all benchmark scenarios while handling up to **175 requests per second** on a local development environment.

Performance remained excellent up to **100 concurrent virtual users**.

Under **250 concurrent users**, the application reached CPU saturation primarily due to BCrypt password verification, resulting in increased response latency while continuing to process all requests successfully.

This behavior is consistent with a production-grade authentication service, where security-sensitive operations prioritize correctness and resilience over maximum throughput.

The benchmarking exercise also led to the discovery and resolution of a concurrency issue involving duplicate refresh tokens, improving the robustness of the authentication system under heavy concurrent load.