# Mercurio Shop — Domain Vocabulary (Step 1.2)

This document defines the ubiquitous language used across all Mercurio Shop microservices.
It is the source of truth for naming in:
- API fields
- DB schemas
- Events
- Logs

## Core Concepts

### Product
A sellable item.
- Identified by: ProductId (UUID)
- Has a SKU (string identifier used by inventory)

### SKU
Stock Keeping Unit. Unique per product variant. Used in inventory operations.
Example: "SKU-IPHONE-15-PRO-256-BLK"

### Price / Money
Monetary amount with a currency.
- Money = amount in minor units (e.g., cents) + currency (e.g., "MXN")

### Order
Represents a purchase request made by a CUSTOMER.
- Identified by: OrderId (UUID)
- Has line items (sku + quantity + unitPrice)
- Belongs to: customerId (from JWT sub)

### Order Status
- PENDING: created, waiting for inventory decision
- CONFIRMED: inventory reserved and order accepted
- REJECTED: inventory reservation failed
- CANCELLED: user/admin cancelled before confirmation

### Inventory Reservation
A request to reserve stock for an order.
- Identified by: reservationId (UUID) or orderId (we will use orderId as idempotency key)

### Import Job
Admin-triggered bulk load of product catalog.
- Identified by: ImportJobId (UUID)
- Status: PENDING, RUNNING, COMPLETED, FAILED

## Events (integration)
Events are immutable, versioned, and contain correlation IDs.

- OrderCreatedEvent (emitted by Order Service)
- InventoryReservedEvent (emitted by Inventory Service)
- InventoryRejectedEvent (emitted by Inventory Service)
- ImportJobCreatedEvent (emitted by Import Service)
- ImportJobCompletedEvent (emitted by Import Service)
