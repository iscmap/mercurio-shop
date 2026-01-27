# Mercurio Shop — Roles & Authorization Model (Step 1.1)

This document defines the **actors, roles, permissions, and JWT contract**
for the Mercurio Shop e-commerce backend.

It is the source of truth for:

- Endpoint authorization rules
- OAuth2/JWT claims expectations
- Ownership enforcement
- Future Spring Security configuration

---

## 1. Roles (Actors)

Mercurio Shop starts with two primary roles:

### CUSTOMER
A customer is an end-user who can:

- Browse the product catalog
- Place orders
- View and cancel their own orders

### ADMIN
An administrator is a back-office user who can:

- Manage products
- Trigger and monitor batch imports
- View all orders for operational purposes

---

## 2. Permissions Matrix

| Capability | CUSTOMER | ADMIN |
|-----------|----------|-------|
| List products | ✅ | ✅ |
| View product details | ✅ | ✅ |
| Create/update/delete product | ❌ | ✅ |
| Place an order | ✅ | ❌ |
| View own orders | ✅ | ✅ (all orders) |
| Cancel own pending order | ✅ | ✅ |
| Upload import file | ❌ | ✅ |
| View import job status | ❌ | ✅ |
| Access health endpoint | ✅ | ✅ |

---

## 3. JWT Claims Contract

All backend services expect an OAuth2/JWT token with these claims.

### Required Claims

- `sub` → unique user identifier
- `roles` → array of assigned roles
- `exp` → expiration timestamp
- `iss` → issuer
- `aud` → intended audience

### Example Token Payload (CUSTOMER)

```json
{
  "sub": "user-123",
  "roles": ["CUSTOMER"],
  "iss": "https://issuer.example",
  "aud": "mercurio-shop",
  "exp": 1893456000
}
