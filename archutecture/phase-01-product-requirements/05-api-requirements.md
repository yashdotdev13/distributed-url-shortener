# API Requirements

## Document Information

| Field | Value |
|-------|-------|
| Document | API Requirements |
| Version | 1.0 |
| Status | Draft |
| Owner | Yash Chauhan |

---

# 1. Introduction

This document defines the API requirements for the Distributed URL Shortener platform.

It identifies the APIs that must be exposed by the platform, their purpose, authentication requirements, expected behavior, and high-level request/response expectations.

Detailed API specifications (OpenAPI/Swagger) will be created during the Low-Level Design phase.

---

# 2. API Design Standards

All APIs shall comply with the standards defined in:

**ADR-001 — API Design Philosophy**

The following standards apply:

- REST APIs
- URI Versioning (`/api/v1`)
- Stateless Communication
- JSON Request/Response
- Standard HTTP Status Codes
- JWT Authentication
- API Keys for Developers
- Correlation IDs
- Idempotency Support
- Standard Error Responses

---

# 3. API Consumers

The platform exposes APIs for the following consumers.

## Guest Users

- Redirect using Short URLs

---

## Authenticated Users

- URL Management
- Dashboard
- Analytics
- API Keys

---

## Administrators

- User Management
- Platform Monitoring
- URL Moderation

---

## Internal Services

- Event Processing
- Analytics
- Notifications

---

# 4. API Categories

The platform exposes the following API groups.

| Category | Purpose |
|----------|----------|
| Authentication | User Authentication |
| URL Management | Create and Manage URLs |
| Redirect | Redirect Requests |
| Analytics | View URL Statistics |
| Dashboard | User Dashboard |
| API Keys | API Key Management |
| Admin | Administrative Operations |
| Health | Service Monitoring |

---

# 5. Authentication APIs

---

## Register User

Method

POST

Endpoint

/api/v1/auth/register

Authentication

Not Required

Purpose

Create a new user account.

---

## Login

Method

POST

Endpoint

/api/v1/auth/login

Authentication

Not Required

Purpose

Authenticate users and issue JWT tokens.

---

## Refresh Token

Method

POST

Endpoint

/api/v1/auth/refresh

Authentication

Refresh Token

Purpose

Issue a new access token.

---

## Logout

Method

POST

Endpoint

/api/v1/auth/logout

Authentication

JWT Required

Purpose

Invalidate user session.

---

# 6. URL Management APIs

---

## Create Short URL

Method

POST

Endpoint

/api/v1/urls

Authentication

JWT Required

Purpose

Create a shortened URL.

Business Rules

- Long URL must be valid.
- Custom alias must be unique.
- Expiration date is optional.
- Idempotency supported.

---

## Get URL Details

Method

GET

Endpoint

/api/v1/urls/{id}

Authentication

JWT Required

Purpose

Retrieve URL information.

---

## List URLs

Method

GET

Endpoint

/api/v1/urls

Authentication

JWT Required

Purpose

Retrieve all URLs created by the authenticated user.

Supports

- Pagination
- Sorting
- Filtering

---

## Update URL

Method

PATCH

Endpoint

/api/v1/urls/{id}

Authentication

JWT Required

Purpose

Update URL metadata.

Examples

- Description
- Expiration Date
- Custom Alias

---

## Delete URL

Method

DELETE

Endpoint

/api/v1/urls/{id}

Authentication

JWT Required

Purpose

Delete an existing URL.

---

# 7. Redirect APIs

---

## Redirect URL

Method

GET

Endpoint

/{shortCode}

Authentication

Not Required

Purpose

Redirect users to the original URL.

Expected Behavior

- Validate Short URL
- Check Expiration
- Check Status
- Redirect using HTTP 302
- Publish Click Event

---

# 8. Analytics APIs

---

## URL Analytics

Method

GET

Endpoint

/api/v1/analytics/{urlId}

Authentication

JWT Required

Purpose

Retrieve analytics for a specific URL.

Metrics

- Total Clicks
- Unique Clicks
- Browser
- Device
- Country
- Referrer
- Daily Clicks

---

## Dashboard Analytics

Method

GET

Endpoint

/api/v1/dashboard

Authentication

JWT Required

Purpose

Retrieve dashboard statistics.

---

# 9. API Key Management

---

## Generate API Key

Method

POST

Endpoint

/api/v1/api-keys

Authentication

JWT Required

Purpose

Generate API Keys.

---

## List API Keys

Method

GET

Endpoint

/api/v1/api-keys

Authentication

JWT Required

Purpose

View active API Keys.

---

## Revoke API Key

Method

DELETE

Endpoint

/api/v1/api-keys/{id}

Authentication

JWT Required

Purpose

Deactivate API Key.

---

# 10. Administrative APIs

---

## List Users

Method

GET

Endpoint

/api/v1/admin/users

Authentication

Admin Only

Purpose

Retrieve registered users.

---

## Disable URL

Method

PATCH

Endpoint

/api/v1/admin/urls/{id}/disable

Authentication

Admin Only

Purpose

Disable malicious URLs.

---

## Platform Metrics

Method

GET

Endpoint

/api/v1/admin/platform

Authentication

Admin Only

Purpose

Retrieve platform statistics.

---

# 11. Health APIs

---

## Health

GET

/health

Purpose

Basic health check.

---

## Liveness

GET

/actuator/health/liveness

Purpose

Kubernetes Liveness Probe.

---

## Readiness

GET

/actuator/health/readiness

Purpose

Kubernetes Readiness Probe.

---

# 12. Pagination

List APIs shall support pagination.

Required Parameters

- page
- size

Optional Parameters

- sort
- direction

Future versions may adopt cursor-based pagination for large datasets.

---

# 13. Filtering

Supported Filters

- Creation Date
- Expiration Date
- Status
- Alias
- Original URL

---

# 14. Sorting

Supported Sort Fields

- Created Date
- Updated Date
- Click Count
- Expiration Date

---

# 15. Standard Response Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 201 | Resource Created |
| 204 | No Content |
| 302 | Redirect |
| 400 | Invalid Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Resource Not Found |
| 409 | Conflict |
| 422 | Validation Failed |
| 429 | Too Many Requests |
| 500 | Internal Server Error |
| 503 | Service Unavailable |

---

# 16. Validation Rules

The platform shall validate:

- URL Format
- Alias Length
- Alias Characters
- Expiration Date
- Request Body
- Authentication Token
- API Key

Invalid requests shall return descriptive validation errors.

---

# 17. Security Requirements

All protected APIs shall:

- Require HTTPS
- Validate JWT Tokens
- Enforce RBAC
- Support Rate Limiting
- Validate Input
- Prevent Injection Attacks

---

# 18. API Traceability

Every API request shall include:

- Correlation ID
- Request Timestamp
- Client IP (Captured)
- User Agent (Captured)

These values support observability and debugging.

---

# 19. Future APIs

The following APIs are planned for future versions.

- QR Code Generation
- Custom Domains
- Team Management
- Billing
- Webhooks
- Public SDK
- Bulk URL Import
- Bulk URL Export

---

# 20. Traceability Matrix

| Functional Requirement | APIs |
|-------------------------|------|
| FR-001 | Register |
| FR-002 | Login |
| FR-003 | Create URL |
| FR-004 | Redirect |
| FR-005 | Update URL |
| FR-006 | Delete URL |
| FR-009 | Analytics |
| FR-010 | Dashboard |
| FR-017 | API Keys |
| FR-018 | Admin APIs |

---

# 21. Conclusion

This document defines the API surface of Version 1 of the Distributed URL Shortener platform.

Detailed endpoint specifications, request schemas, response schemas, OpenAPI documentation, and service contracts will be produced during the Low-Level Design phase.

This document serves as the bridge between Product Requirements and System Design.