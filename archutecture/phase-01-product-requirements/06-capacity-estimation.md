# Capacity Estimation

## Document Information

| Field | Value |
|-------|-------|
| Document | Capacity Estimation |
| Version | 1.0 |
| Status | Draft |
| Owner | Yash Chauhan |

---

# 1. Introduction

This document estimates the expected workload of the Distributed URL Shortener platform.

Capacity estimation helps determine:

- Infrastructure requirements
- Database sizing
- Cache sizing
- Network bandwidth
- Kafka throughput
- Horizontal scaling requirements

These numbers are estimates intended to guide architectural decisions.

---

# 2. Assumptions

The following assumptions are made for Version 1.

| Metric | Value |
|---------|------|
| Registered Users | 1 Million |
| Daily Active Users | 100,000 |
| URLs Created Per Day | 500,000 |
| Average Redirects Per Day | 10 Million |
| Peak Traffic Multiplier | 5x |
| Average URL Length | 250 Bytes |
| Short Code Length | 8 Characters |

---

# 3. Read vs Write Ratio

The workload is heavily read-oriented.

URL Creation (Writes)

500,000/day

Redirect Requests (Reads)

10,000,000/day

Read : Write Ratio

20 : 1

---

# 4. Traffic Estimation

Daily Redirect Requests

10,000,000

Requests Per Second

10,000,000 / 86400

≈ 116 RPS

Peak Traffic (5x)

≈ 600 RPS

Future Target

5,000+ RPS

---

# 5. URL Creation Traffic

URLs Created Daily

500,000

Requests Per Second

≈ 6 RPS

Peak

≈ 30 RPS

Observation

URL creation traffic is relatively small.

Redirect traffic dominates the system.

---

# 6. Database Storage

Each URL Record contains approximately

| Field | Size |
|--------|------|
| URL ID | 8 B |
| Original URL | 250 B |
| Short Code | 8 B |
| User ID | 8 B |
| Metadata | 100 B |
| Timestamps | 16 B |
| Status | 4 B |

Approximate Record Size

≈ 400 Bytes

---

URLs Per Year

500,000 × 365

≈ 182 Million URLs

Storage

182M × 400 Bytes

≈ 73 GB/year

Rounded

≈ 100 GB/year including indexes and overhead.

---

# 7. Analytics Storage

Every redirect generates one analytics event.

Redirects Per Day

10 Million

Analytics Events Per Year

≈ 3.65 Billion

Assuming

100 Bytes/Event

Storage

≈ 365 GB/year

With indexes and metadata

≈ 500 GB/year

Observation

Analytics grows much faster than URL metadata.

---

# 8. Redis Cache Estimation

Assume

10% of URLs receive 90% of traffic.

Popular URLs

18 Million

Initially cache

Top 1 Million URLs

Average Cache Entry

≈ 350 Bytes

Memory

≈ 350 MB

Including Redis overhead

≈ 700 MB

Recommended Redis Memory

2 GB initially.

---

# 9. Kafka Throughput

Redirect Events

10 Million/day

Events Per Second

≈ 116

Peak

≈ 600

Future

5,000+ Events/sec

Kafka can easily support this workload.

---

# 10. Network Bandwidth

Average Redirect Response

≈ 1 KB

10 Million Redirects

≈ 10 GB/day

Analytics Events

≈ 100 Bytes

≈ 1 GB/day

Expected Total

≈ 15 GB/day

---

# 11. Growth Projection

| Year | URLs | URL Storage | Analytics Storage |
|------|------|-------------|-------------------|
| 1 | 182 M | 100 GB | 500 GB |
| 2 | 364 M | 200 GB | 1 TB |
| 3 | 546 M | 300 GB | 1.5 TB |
| 5 | 910 M | 500 GB | 2.5 TB |

---

# 12. Scaling Implications

Based on the above estimates:

The platform should support:

- Horizontal Service Scaling
- Redis Cluster
- PostgreSQL Read Replicas
- Kafka Partitioning
- Kubernetes Autoscaling

Future enhancements include:

- Database Sharding
- Multi-Region Deployment
- CDN Integration

---

# 13. Risks

Potential capacity risks include:

- Hot URLs causing uneven traffic distribution
- Redis memory exhaustion
- Analytics database growth
- Kafka consumer lag
- Storage cost increase

These risks will be addressed during the System Design phase.

---

# 14. Conclusion

The workload is predominantly read-heavy.

Key observations:

- Redirect traffic is significantly higher than URL creation traffic.
- Analytics data grows much faster than URL metadata.
- Redis caching is essential for low-latency redirects.
- Kafka enables asynchronous analytics processing.
- The architecture must prioritize horizontal scalability and future data partitioning.