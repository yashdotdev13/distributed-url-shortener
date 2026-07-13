# ADR-001: API Design Philosophy

| Field | Value |
|-------|-------|
| ADR ID | ADR-001 |
| Title | API Design Philosophy |
| Status | Accepted |
| Date | YYYY-MM-DD |
| Authors | Yash Chauhan |

---

# 1. Context

The Distributed URL Shortener platform exposes APIs to multiple types of clients.

These include:

- Web Dashboard
- Mobile Applications
- Third-Party Developers
- Internal Services
- Future SDKs

Since APIs are the primary interface between clients and the platform, establishing consistent API design principles is critical.

Without common standards, APIs become inconsistent, difficult to maintain, and challenging to evolve over time.

This Architecture Decision Record defines the standards that every service within the platform must follow.

---

# 2. Problem Statement

The platform requires a standardized API design strategy that ensures:

- Consistency
- Simplicity
- Scalability
- Maintainability
- Backward Compatibility
- Observability
- Security

The challenge is selecting an API design approach that balances developer experience with production-grade engineering practices.

---

# 3. Decision Summary

The following decisions have been made.

| Area | Decision |
|------|----------|
| API Style | REST |
| Internal Communication | REST (Initial Version) |
| Event Communication | Kafka |
| Versioning | URI Versioning |
| Authentication | JWT + API Keys |
| Public Redirect | No Authentication |
| Redirect Status Code | HTTP 302 |
| Resource Naming | Plural Nouns |
| Error Format | Standardized JSON |
| Correlation ID | Required |
| Idempotency | Required for Create Operations |

---

# 4. API Style

## Decision

The platform will expose RESTful APIs.

Example

POST /api/v1/urls

GET /api/v1/urls

PATCH /api/v1/urls/{id}

DELETE /api/v1/urls/{id}

---

## Alternatives Considered

### GraphQL

Pros

- Flexible Queries
- No Over Fetching

Cons

- Complex Caching
- Complex Authorization
- Difficult Monitoring
- Increased Operational Complexity

Decision

Rejected.

Reason

The platform primarily exposes CRUD-oriented APIs where REST provides better simplicity and tooling.

---

### gRPC

Pros

- High Performance
- Binary Protocol
- Streaming Support

Cons

- Browser Incompatibility
- Additional Gateway Required
- Higher Learning Curve

Decision

Deferred.

Future internal service communication may migrate to gRPC after system maturity.

---

# 5. API Versioning

## Decision

URI Versioning

Example

/api/v1/

---

## Alternatives

Header Versioning

Rejected due to lower discoverability.

Query Parameter Versioning

Rejected due to poor API design practices.

---

## Rationale

URI versioning is:

- Easy to understand
- Easy to debug
- Easy to document
- Widely adopted
- Gateway friendly

---

# 6. Resource Naming

Resources shall use plural nouns.

Examples

/users

/urls

/api-keys

/analytics

Good

GET /api/v1/urls

Bad

GET /api/v1/getUrls

Bad

POST /api/v1/createShortUrl

Reason

REST APIs represent resources rather than actions.

---

# 7. HTTP Methods

| Method | Purpose |
|---------|----------|
| GET | Retrieve Resource |
| POST | Create Resource |
| PUT | Replace Resource |
| PATCH | Partial Update |
| DELETE | Remove Resource |

---

# 8. Redirect Strategy

## Decision

The platform shall use HTTP 302 Found for URL redirects.

---

## Alternatives

### HTTP 301

Rejected.

Reason

Browsers cache permanent redirects.

This bypasses the platform for future requests, preventing:

- Analytics Collection
- URL Expiration Checks
- Malicious Link Detection
- Dynamic Destination Updates

---

### HTTP 302

Accepted.

Reason

Every redirect request reaches the platform.

This allows:

- Click Tracking
- Analytics Collection
- Dynamic Redirects
- Runtime Validation
- Future Feature Expansion

---

# 9. Authentication Strategy

The platform supports multiple authentication mechanisms.

---

## Public Redirect APIs

Authentication

None

Reason

Short URLs must be publicly accessible.

Example

GET /abc123

---

## User Management APIs

Authentication

JWT Bearer Token

Example

Authorization: Bearer <JWT>

Reason

Secure authenticated user operations.

---

## Developer APIs

Authentication

API Keys

Example

X-API-Key: xxxxxxxxx

Reason

Third-party integrations should not require interactive login.

---

## Internal Service Communication

Initial Version

JWT Service Tokens

Future

Mutual TLS (mTLS)

---

# 10. Standard Response Format

Successful responses shall follow a common envelope.

Example

```json
{
  "success": true,
  "data": {},
  "metadata": {}
}
```

---

Error responses shall also be standardized.

Example

```json
{
  "success": false,
  "error": {
    "code": "URL_NOT_FOUND",
    "message": "Requested URL does not exist."
  }
}
```

Reason

Consistent client-side handling.

---

# 11. Correlation IDs

Every incoming request shall contain a Correlation ID.

Header

X-Correlation-ID

If absent,

the API Gateway shall generate one.

The Correlation ID shall propagate through:

- API Gateway
- URL Service
- Kafka Messages
- Analytics Service
- Notification Service

Reason

Supports distributed tracing and debugging.

---

# 12. Idempotency

POST operations that create resources shall support idempotent retries.

Clients may provide

Idempotency-Key

header.

If duplicate requests are received with the same key,

the previously generated response shall be returned.

Reason

Prevents duplicate resource creation during retries.

---

# 13. Rate Limiting Headers

Protected APIs shall expose:

X-RateLimit-Limit

X-RateLimit-Remaining

Retry-After

Reason

Allows clients to react appropriately when limits are reached.

---

# 14. HTTP Status Codes

The platform shall consistently use standard HTTP status codes.

| Code | Meaning |
|------|----------|
| 200 | Success |
| 201 | Created |
| 202 | Accepted |
| 204 | No Content |
| 302 | Redirect |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 409 | Conflict |
| 422 | Validation Failed |
| 429 | Too Many Requests |
| 500 | Internal Server Error |
| 503 | Service Unavailable |

---

# 15. Design Principles

Every API developed within this platform shall adhere to the following principles.

- Consistent
- Predictable
- Stateless
- Secure
- Versioned
- Observable
- Backward Compatible
- RESTful
- Easy to Consume

---

# 16. Consequences

## Positive

- Consistent API ecosystem
- Easier documentation
- Simplified testing
- Easier onboarding
- Better observability
- Easier client integration

---

## Negative

- URI versioning requires endpoint duplication for major changes.
- REST may require multiple requests compared to GraphQL.

These trade-offs are acceptable for the current platform scope.

---

# 17. References

- REST Architectural Style (Roy Fielding)
- RFC 9110 – HTTP Semantics
- OpenAPI Specification 3.1
- Microsoft REST API Guidelines
- Google API Design Guide