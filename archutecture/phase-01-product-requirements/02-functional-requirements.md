# Functional Requirements

## Document Information

| Field | Value |
|-------|-------|
| Document | Functional Requirements |
| Version | 1.0 |
| Status | Draft |
| Owner | Yash Chauhan |

---

# 1. Introduction

This document defines the functional capabilities of the Distributed URL Shortener platform.

Each requirement describes **what the system must do** without specifying **how it should be implemented**.

All functional requirements are uniquely identified to support traceability during design, implementation, testing, and maintenance.

---

# 2. Actors

The system supports the following actors.

## Guest User

An unauthenticated user who can access public shortened URLs.

---

## Authenticated User 


A registered user who manages URLs.

---

## Administrator

Responsible for platform administration.

---

## Internal Services

Background services responsible for analytics, caching, notifications, and event processing.

---

# 3. Functional Requirements

---

## FR-001 User Registration

### Description

The system shall allow new users to register using their email address and password.

### Priority

High

### Actor

Guest User

### Preconditions

- User is not already registered.

### Postconditions

- User account is created.
- Verification process may be initiated.

### Acceptance Criteria

- User provides valid email.
- Password satisfies policy.
- Duplicate emails are rejected.

---

## FR-002 User Authentication

### Description

The system shall authenticate registered users.

### Priority

High

### Actor

Registered User

### Acceptance Criteria

- Valid credentials return JWT.
- Invalid credentials return authentication error.

---

## FR-003 Create Short URL

### Description

The system shall generate a unique short URL for a valid long URL.

### Priority

Critical

### Actor

Authenticated User

### Acceptance Criteria

- Short URL is unique.
- Original URL is stored.
- Response contains shortened URL.

---

## FR-004 URL Redirection

### Description

The system shall redirect users from a short URL to its original destination.

### Priority

Critical

### Actor

Guest User

### Acceptance Criteria

- Valid URLs return HTTP 302.
- Expired URLs return appropriate error.
- Invalid URLs return HTTP 404.

---

## FR-005 Update URL

The system shall allow users to modify URL metadata.

Examples include:

- Expiration Date
- Custom Alias
- Description

---

## FR-006 Delete URL

Users shall be able to permanently delete URLs they own.

---

## FR-007 URL Expiration

The system shall support automatic expiration of URLs.

Expired URLs must not redirect users.

---

## FR-008 Custom Alias

Users may provide a custom alias while creating URLs.

The system shall validate alias uniqueness.

---

## FR-009 URL Analytics

The platform shall collect analytics including:

- Total Clicks
- Unique Clicks
- Browser
- Device
- Country
- Referrer
- Timestamp

Analytics collection must not delay redirects.

---

## FR-010 User Dashboard

Authenticated users shall be able to view:

- Created URLs
- Total Clicks
- Active Links
- Expired Links
- Analytics Summary

---

## FR-011 Search URLs

Users shall be able to search URLs by:

- Original URL
- Short URL
- Alias

---

## FR-012 Rate Limiting

The platform shall limit API requests to prevent abuse.

Limits may differ based on API and user tier.

---

## FR-013 Health Checks

Each service shall expose health endpoints.

---

## FR-014 Logging

The platform shall log significant application events.

---

## FR-015 Event Publishing

Important business events shall be published for asynchronous processing.

Examples:

- URL Created
- URL Updated
- URL Deleted
- URL Clicked

---

## FR-016 Notifications

The platform shall notify users regarding important events.

Examples:

- URL Expired
- URL Near Expiration
- Suspicious Activity

---

## FR-017 API Key Management

Users shall be able to generate and revoke API Keys.

---

## FR-018 Role-Based Authorization

The system shall support role-based authorization.

Supported roles include:

- USER
- ADMIN

---

## FR-019 Audit Trail

The platform shall maintain an audit trail of critical operations.

---

## FR-020 Monitoring

Operational metrics shall be collected for all services.

---

# 4. Functional Requirement Summary

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-001 | Registration | High |
| FR-002 | Login | High |
| FR-003 | Create URL | Critical |
| FR-004 | Redirect URL | Critical |
| FR-005 | Update URL | High |
| FR-006 | Delete URL | High |
| FR-007 | Expiration | Medium |
| FR-008 | Custom Alias | Medium |
| FR-009 | Analytics | High |
| FR-010 | Dashboard | Medium |
| FR-011 | Search | Medium |
| FR-012 | Rate Limiting | High |
| FR-013 | Health Checks | High |
| FR-014 | Logging | High |
| FR-015 | Event Publishing | Critical |
| FR-016 | Notifications | Low |
| FR-017 | API Keys | High |
| FR-018 | RBAC | High |
| FR-019 | Audit Trail | Medium |
| FR-020 | Monitoring | High |

---

# 5. Traceability

Every functional requirement defined in this document shall be traceable to:

- API Design
- Database Design
- Service Design
- Test Cases
- Deployment