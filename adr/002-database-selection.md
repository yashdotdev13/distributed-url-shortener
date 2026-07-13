# ADR-002: Database Selection

| Field | Value |
|-------|-------|
| ADR ID | ADR-002 |
| Title | Primary Database Selection |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The Distributed URL Shortener platform requires a primary database to store transactional business data.

This includes:

- Users
- URLs
- API Keys
- URL Metadata
- Link Ownership
- Expiration Information

The selected database must provide:

- Strong consistency
- Transaction support
- Reliable indexing
- High availability
- Future scalability
- Mature ecosystem
- Excellent Spring Boot support

The system is expected to evolve from a relatively small deployment to a large-scale distributed platform serving millions of requests per day.

Selecting the correct database is one of the most important architectural decisions because changing the primary database after production adoption is costly and operationally risky.

---

# 2. Problem Statement

The platform requires a database capable of supporting transactional workloads while remaining scalable as traffic increases.

The challenge is determining whether to adopt a traditional relational database or a distributed NoSQL database from the beginning.

The decision must balance:

- Simplicity
- Reliability
- Scalability
- Operational Complexity
- Development Productivity
- Long-Term Maintainability

---

# 3. Decision Drivers

The following criteria were used during evaluation.

| Requirement | Importance |
|-------------|------------|
| ACID Transactions | Critical |
| Strong Consistency | Critical |
| Query Flexibility | High |
| Indexing Support | High |
| Operational Simplicity | High |
| Scalability | High |
| Spring Boot Integration | High |
| Community Support | Medium |
| Future Sharding Support | Medium |
| Cloud Compatibility | High |

---

# 4. Alternatives Considered

## Option 1 — PostgreSQL

### Advantages

- ACID compliant
- Strong consistency
- Excellent indexing capabilities
- Mature ecosystem
- Powerful SQL support
- JSONB support
- Read replica support
- Native partitioning
- Reliable backup and recovery
- Excellent Spring Boot integration
- Large community support

### Disadvantages

- Vertical scaling has practical limits
- Native sharding requires additional planning
- More operational work than managed NoSQL databases

---

## Option 2 — MySQL

### Advantages

- Mature ecosystem
- High performance
- Wide industry adoption

### Disadvantages

- Less advanced indexing features compared to PostgreSQL
- JSON support is less mature
- Fewer advanced SQL capabilities
- Less flexible for future analytical workloads

---

## Option 3 — MongoDB

### Advantages

- Flexible document schema
- Rapid development
- Horizontal scaling support

### Disadvantages

- Weaker transactional guarantees
- Complex ownership relationships
- Reduced relational integrity
- Joins are less efficient

---

## Option 4 — Cassandra

### Advantages

- Massive write throughput
- Horizontal scalability
- High availability
- Multi-region support

### Disadvantages

- Eventually consistent
- Limited query flexibility
- No relational model
- Operational complexity
- Unsuitable for transactional workloads

---

## Option 5 — Amazon DynamoDB

### Advantages

- Fully managed
- Automatic scaling
- Low operational overhead

### Disadvantages

- Vendor lock-in
- AWS dependency
- Cost grows with traffic
- Limited querying flexibility

---

# 5. Evaluation

| Database | ACID | Scalability | Complexity | Fit for V1 |
|----------|------|-------------|------------|------------|
| PostgreSQL | Excellent | High | Low | ⭐⭐⭐⭐⭐ |
| MySQL | Good | Medium | Low | ⭐⭐⭐ |
| MongoDB | Medium | High | Medium | ⭐⭐⭐ |
| Cassandra | Poor | Excellent | High | ⭐⭐ |
| DynamoDB | Medium | Excellent | Medium | ⭐⭐⭐ |

---

# 6. Decision

The platform will use **PostgreSQL** as the primary transactional database.

PostgreSQL will be responsible for storing:

- User Accounts
- Short URLs
- URL Metadata
- API Keys
- Link Ownership
- Expiration Rules

---

# 7. Rationale

The primary workload of the platform is transactional rather than analytical.

Business operations such as URL creation, authentication, ownership validation, and metadata updates require strong consistency and transactional guarantees.

Although redirect traffic is expected to be significantly higher than write traffic, redirect requests will primarily be served through Redis.

Therefore, the database is **not expected to receive the majority of production traffic**.

Instead of replacing the database to improve scalability, the platform will first optimize the surrounding architecture.

This includes:

- Redis caching
- Read replicas
- Efficient indexing
- Connection pooling
- Query optimization
- Table partitioning
- Horizontal service scaling

Changing the database engine will only be considered if these optimizations become insufficient.

---

# 8. Scalability Strategy

The database evolution strategy is intentionally incremental.

## Phase 1

Single PostgreSQL instance.

---

## Phase 2

Introduce Redis caching.

---

## Phase 3

Add PostgreSQL read replicas.

---

## Phase 4

Implement table partitioning.

---

## Phase 5

Optimize indexes and query execution plans.

---

## Phase 6

Introduce database sharding if required.

---

## Phase 7

Evaluate alternative distributed databases only if PostgreSQL can no longer satisfy business requirements.

---

# 9. Architecture Principle

**Scale the bottleneck, not the architecture.**

Before replacing a technology, identify the actual bottleneck and optimize that component.

Technologies should only be replaced when they become the limiting factor after reasonable optimizations have been exhausted.

---

# 10. Consequences

## Positive

- Strong transactional guarantees
- Reliable consistency
- Mature tooling
- Easier development
- Excellent Spring Boot integration
- Lower operational complexity
- Easier onboarding
- Rich SQL capabilities

---

## Negative

- Eventual need for sharding at extreme scale
- Vertical scaling limits
- Additional operational work for replication

These trade-offs are acceptable for the expected workload.

---

# 11. Risks

Potential risks include:

- Rapid database growth
- Inefficient queries
- Missing indexes
- Hot partitions
- Storage cost increase

These risks will be mitigated through:

- Continuous monitoring
- Performance tuning
- Capacity planning
- Read replicas
- Redis caching

---

# 12. Future Evolution

Future platform versions may introduce:

- Read Replicas
- Table Partitioning
- Logical Replication
- Multi-Region Replication
- Database Sharding
- Polyglot Persistence

The introduction of additional databases will be driven by measurable business requirements rather than anticipated future scale.

---

# 13. Decision Summary

| Category | Decision |
|----------|----------|
| Primary Database | PostgreSQL |
| Consistency Model | Strong Consistency |
| Transactions | ACID |
| Scaling Strategy | Incremental |
| Cache | Redis |
| Future Scaling | Read Replicas → Partitioning → Sharding |
| Database Replacement | Last Resort |

---

# 14. References

- PostgreSQL Official Documentation
- Designing Data-Intensive Applications — Martin Kleppmann
- PostgreSQL High Performance
- AWS Well-Architected Framework
- Google Cloud Architecture Framework