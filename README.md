# BackEnd
Backend API for a logistics management platform supporting shipment booking, tracking, agent assignment, pricing, payments, complaint resolution, and administrative operations using Spring Boot, JPA, MySQL, and JWT authentication.

# Database Schema

**Legend:**
- 🟢 `original` — field from your original design
- 🟡 `requested` — field you asked to restore/add explicitly
- 🔵 `suggested` — field added on my recommendation, not in your original list

---

## Table of Contents

- [User](#user)
- [AgentProfile](#agentprofile)
- [Address](#address)
- [City](#city)
- [PricingRule](#pricingrule)
- [Shipment](#shipment)
- [ShipmentTrackingEvent](#shipmenttrackingevent)
- [Payment](#payment)
- [Complaint](#complaint)
- [ComplaintAttachment](#complaintattachment)
- [Entity Relationship Summary](#entity-relationship-summary)

---
## Mapped super class
| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `id` | `Long` | PK, IDENTITY | 🟢 | |
| `createdAt` | `LocalDateTime` | NOT NULL, not updatable | 🔵 | audit trail |
| `updatedAt` | `LocalDateTime` | | 🔵 | audit trail |
| `status` | `Enum (Status)` | NOT NULL | 🟢 | `ACTIVE`, `INACTIVE`, `DELETED` |
## User

Table: `users`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `fullName` | `String` | NOT NULL | 🟢 | |
| `email` | `String` | NOT NULL, UNIQUE | 🟢 | |
| `password` | `String` | NOT NULL | 🟢 | store as hash, never plaintext |
| `phoneNumber` | `String` | NOT NULL | 🟢 | |
| `role` | `Enum (Role)` | NOT NULL | 🟢 | `ROLE_CUSTOMER`, `ROLE_AGENT`, `ROLE_ADMIN`, `ROLE_SUPER_ADMIN` |
<!-- | `deleted` | `boolean` | NOT NULL, default `false` | 🔵 | soft-delete flag | -->

---

## AgentProfile

Table: `agent_profiles`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `user` | `User` (`@OneToOne`) | FK -> `users.id` | 🟢 | shared-PK relationship |
| `employeeCode` | `String` | UNIQUE | 🟡 | internal ops/manifest tracking |
| `joiningDate` | `LocalDate` | NOT NULL | 🟢 | |
| `commission` | `BigDecimal(10,2)` | | 🟢 | |
<!-- | `vehicleNumber` | `String` | | 🟡 | capacity/zone planning | -->
| `aadhaarNumber` | `String` | NOT NULL, UNIQUE | 🟢 | sensitive PII — encrypt/mask at application layer |

---

## Address

Table: `addresses`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `user` | `User` (`@ManyToOne`) | FK -> `users.id`, NOT NULL | 🟢 | |
| `label` | `String` | | 🟡 | e.g. "Home", "Office", "Warehouse" |
| `contactPerson` | `String` | NOT NULL | 🟢 | |
| `contactNumber` | `String` | NOT NULL | 🟢 | |
| `address` | `String(500)` | NOT NULL | 🟢 | |
| `city` | `City` (`@ManyToOne`) | FK -> `cities.id`, NOT NULL | 🟢 | |
| `postalCode` | `String` | NOT NULL | 🟢 | |
| `createdAt` | `LocalDateTime` | NOT NULL, not updatable | 🔵 | audit trail |
| `updatedAt` | `LocalDateTime` | | 🔵 | audit trail |

---

## City

Table: `cities`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `cityName` | `String` | NOT NULL | 🟢 | |
| `state` | `String` | NOT NULL | 🟢 | |
| `country` | `String` | NOT NULL | 🟢 | |
| `pincode` | `String` | NOT NULL | 🟢 | |
| `latitude` | `Double` | NOT NULL | 🟡 | for distance calc; will be replaced/supplemented by Maps API later |
| `longitude` | `Double` | NOT NULL | 🟡 | for distance calc; will be replaced/supplemented by Maps API later |

---

## PricingRule

Table: `pricing_rules`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `ruleName` | `String` | NOT NULL | 🟢 | |
| `baseCharge` | `BigDecimal(10,2)` | NOT NULL | 🟢 | |
| `weightRate` | `BigDecimal(10,2)` | NOT NULL | 🟢 | |
| `agentCommissionPercentage` | `BigDecimal(5,2)` | NOT NULL | 🟢 | |
| `minimumCharge` | `BigDecimal(10,2)` | NOT NULL | 🟢 | |
| `effectiveFrom` | `LocalDate` | NOT NULL | 🟢 | |
| `currency` | `String(3)` | NOT NULL, default `"INR"` | 🔵 | multi-currency support |

---

## Shipment

Table: `shipments`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `trackingNumber` | `String` | NOT NULL, UNIQUE | 🟡 | human-friendly tracking code |
| `customer` | `User` (`@ManyToOne`) | FK -> `users.id`, NOT NULL | 🟢 | |
| `assignedAgent` | `User` (`@ManyToOne`) | FK -> `users.id` | 🟢 | |
| `pickupAddress` | `Address` (`@ManyToOne`) | FK -> `addresses.id`, NOT NULL | 🟢 | |
| `deliveryAddress` | `Address` (`@ManyToOne`) | FK -> `addresses.id`, NOT NULL | 🟢 | |
| `sourceCity` | `City` (`@ManyToOne`) | FK -> `cities.id`, NOT NULL | 🔵 | denormalized from `pickupAddress.city` for fast filtering |
| `destinationCity` | `City` (`@ManyToOne`) | FK -> `cities.id`, NOT NULL | 🔵 | denormalized from `deliveryAddress.city` |
| `packageType` | `String` | NOT NULL | 🟢 | |
| `packageDescription` | `String(1000)` | | 🟢 | |
| `weight` | `BigDecimal(10,3)` | NOT NULL | 🟢 | in kg |
| `volume` | `BigDecimal(10,3)` | | 🟢 | calculated from dimensions |
| `pricingRule` | `PricingRule` (`@ManyToOne`) | FK -> `pricing_rules.id`, NOT NULL | 🟢 | |
| `shippingCharge` | `BigDecimal(10,2)` | NOT NULL | 🟡 | computed charge, frozen at booking time |
| `expectedDeliveryDate` | `LocalDate` | | 🟢 | |
| `actualDeliveryDate` | `LocalDate` | | 🟢 | |
| `status` | `Enum (ShipmentStatus)` | NOT NULL | 🟢 | `BOOKED`, `PICKED_UP`, `IN_TRANSIT`, `OUT_FOR_DELIVERY`, `DELIVERED`, `CANCELLED`, `RETURNED` |

---

## ShipmentTrackingEvent

Table: `shipment_tracking_events`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `shipment` | `Shipment` (`@ManyToOne`) | FK -> `shipments.id`, NOT NULL | 🟢 | |
| `status` | `Enum (Shipment.ShipmentStatus)` | NOT NULL | 🟢 | reuses `Shipment`'s status enum |
| `remarks` | `String(1000)` | | 🟢 | |
| `updatedBy` | `User` (`@ManyToOne`) | FK -> `users.id`, NOT NULL | 🟢 | |

---

## Payment

Table: `payments`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `shipment` | `Shipment` (`@ManyToOne`) | FK -> `shipments.id`, NOT NULL | 🔵 | changed from implied 1:1 to many-to-one to support partial payments/refunds/retries |
| `transactionId` | `String` | NOT NULL, UNIQUE | 🟢 | |
| `amount` | `BigDecimal(10,2)` | NOT NULL | 🟢 | |
| `paymentMethod` | `Enum (PaymentMethod)` | NOT NULL | 🟢 | `CASH_ON_DELIVERY`, `CREDIT_CARD`, `DEBIT_CARD`, `UPI`, `NET_BANKING`, `WALLET` |
| `gatewayName` | `String` | | 🟡 | needed for reconciliation/disputes across gateways |
| `paymentStatus` | `Enum (PaymentStatus)` | NOT NULL | 🟢 | `PENDING`, `SUCCESS`, `FAILED`, `REFUNDED` |

---

## Complaint

Table: `complaints`

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `shipment` | `Shipment` (`@ManyToOne`) | FK -> `shipments.id`, NOT NULL | 🟢 | |
| `customer` | `User` (`@ManyToOne`) | FK -> `users.id`, NOT NULL | 🟢 | |
| `assignedTo` | `User` (`@ManyToOne`) | FK -> `users.id` | 🟢 | |
| `complaintType` | `Enum (ComplaintType)` | NOT NULL | 🟢 | `DAMAGED_PACKAGE`, `LOST_PACKAGE`, `DELAYED_DELIVERY`, `WRONG_DELIVERY`, `BEHAVIOUR_ISSUE`, `OTHER` |
| `description` | `String(1000)` | NOT NULL | 🟢 | |
| `resolutionRemarks` | `String(1000)` | | 🟢 | |
| `status` | `Enum (ComplaintStatus)` | NOT NULL | 🟢 | `OPEN`, `IN_PROGRESS`, `RESOLVED`, `REJECTED`, `CLOSED` |

---

## ComplaintAttachment

Table: `complaint_attachments`

> 🔵 Entirely new table, not in your original design. Remove this entity if you don't want it yet.

| Field | Type | Constraints | Tag | Notes |
|---|---|---|---|---|
| `id` | `Long` | PK, IDENTITY | 🔵 | |
| `complaint` | `Complaint` (`@ManyToOne`) | FK -> `complaints.id`, NOT NULL | 🔵 | |
| `fileUrl` | `String(1000)` | NOT NULL | 🔵 | |
| `fileName` | `String` | | 🔵 | |
| `uploadedAt` | `LocalDateTime` | NOT NULL, not updatable | 🔵 | |

---

## Entity Relationship Summary

```text
User ──┬─ 1:1 ──> AgentProfile (shared PK)
       ├─ 1:N ──> Address
       ├─ 1:N ──> Shipment        (as customer)
       ├─ 1:N ──> Shipment        (as assignedAgent)
       ├─ 1:N ──> ShipmentTrackingEvent (as updatedBy)
       ├─ 1:N ──> Complaint       (as customer)
       └─ 1:N ──> Complaint       (as assignedTo)

City ──┬─ 1:N ──> Address
       ├─ 1:N ──> Shipment (as sourceCity)
       └─ 1:N ──> Shipment (as destinationCity)

Address ──── 1:N ──> Shipment (as pickupAddress / deliveryAddress)

PricingRule ── 1:N ──> Shipment

Shipment ──┬─ 1:N ──> ShipmentTrackingEvent
           ├─ 1:N ──> Payment
           └─ 1:N ──> Complaint

Complaint ── 1:N ──> ComplaintAttachment
```
