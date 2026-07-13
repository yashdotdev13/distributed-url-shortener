# User Stories

## Document Information

| Field | Value |
|-------|-------|
| Document | User Stories |
| Version | 1.0 |
| Status | Draft |
| Owner | Yash Chauhan |

---

# 1. Introduction

This document captures the interactions between different actors and the Distributed URL Shortener platform.

Each user story describes a goal from the perspective of a user.

The stories will serve as the foundation for API design, database design, authorization, testing, and service decomposition.

---

# 2. Actors

The platform consists of the following actors:

- Guest User
- Authenticated User
- Administrator
- Internal System

---

# 3. Guest User Stories

---

## US-001 Open Short URL

**As a Guest User**

I want to open a shortened URL

So that I can reach the original destination.

### Acceptance Criteria

- Valid URL redirects successfully.
- Expired URLs return an appropriate error.
- Invalid URLs return 404.

---

## US-002 View Error Page

**As a Guest User**

I want meaningful error pages

So that I understand why the URL cannot be accessed.

---

# 4. Authenticated User Stories

---

## US-003 Register

As a Guest User,

I want to create an account

So that I can manage my shortened URLs.

---

## US-004 Login

As a Registered User,

I want to log in

So that I can securely access my dashboard.

---

## US-005 Create URL

As an Authenticated User,

I want to shorten a long URL

So that I can easily share it.

---

## US-006 Create Custom Alias

As an Authenticated User,

I want to specify a custom alias

So that my shortened link is easy to remember.

---

## US-007 Update URL

As an Authenticated User,

I want to modify URL metadata

So that I can manage my links.

Examples:

- Description
- Expiration Date
- Alias

---

## US-008 Delete URL

As an Authenticated User,

I want to delete URLs

So that unused links are removed.

---

## US-009 Search URLs

As an Authenticated User,

I want to search my URLs

So that I can quickly find them.

---

## US-010 View Dashboard

As an Authenticated User,

I want to view all my URLs

So that I can manage them efficiently.

---

## US-011 View Analytics

As an Authenticated User,

I want to view analytics

So that I understand link performance.

Metrics include:

- Total Clicks
- Daily Clicks
- Browser
- Device
- Country
- Referrer

---

## US-012 Generate API Key

As an Authenticated User,

I want to generate API Keys

So that external applications can access the platform.

---

## US-013 Revoke API Key

As an Authenticated User,

I want to revoke API Keys

So that compromised credentials become unusable.

---

# 5. Administrator Stories

---

## US-014 Manage Users

As an Administrator,

I want to manage users

So that the platform remains secure.

---

## US-015 Disable Malicious URLs

As an Administrator,

I want to disable malicious links

So that harmful content cannot be distributed.

---

## US-016 View Platform Health

As an Administrator,

I want to monitor platform health

So that operational issues can be detected.

---

# 6. Internal System Stories

---

## US-017 Process Analytics

As the Analytics Service,

I want to consume click events

So that analytics can be updated asynchronously.

---

## US-018 Cache Popular URLs

As the Redirect Service,

I want frequently accessed URLs to be cached

So that redirects remain fast.

---

## US-019 Publish Events

As the URL Service,

I want to publish business events

So that downstream services remain synchronized.

---

## US-020 Retry Failed Processing

As the Event Processing System,

I want failed messages to be retried

So that transient failures are automatically recovered.

---

# 7. Future User Stories

The following stories are intentionally deferred.

- QR Code Generation
- Team Workspaces
- Custom Domains
- Enterprise Billing
- Link Preview
- AI Spam Detection
- Deep Links
- Public SDK

---

# 8. Story Mapping

| Story | Functional Requirement |
|--------|------------------------|
| US-001 | FR-004 |
| US-003 | FR-001 |
| US-004 | FR-002 |
| US-005 | FR-003 |
| US-006 | FR-008 |
| US-007 | FR-005 |
| US-008 | FR-006 |
| US-009 | FR-011 |
| US-010 | FR-010 |
| US-011 | FR-009 |
| US-012 | FR-017 |
| US-014 | FR-018 |
| US-017 | FR-015 |
| US-018 | FR-003 |
| US-019 | FR-015 |
| US-020 | NFR-009 |