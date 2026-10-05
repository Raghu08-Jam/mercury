# Mercury — E-Commerce Backend

Mercury is a full-featured e-commerce backend built with Spring Boot and PostgreSQL, covering the complete customer journey from browsing products to checkout, payment, and order history — with real authentication, authorization, and transactional integrity, not just CRUD.

It was built from the ground up as a learning-by-building project, with every design decision (schema, relationships, security model) reasoned through deliberately rather than copy-pasted.

## Tech Stack

- **Java** (Spring Boot)
- **Spring Data JPA / Hibernate** — ORM and relationship management
- **PostgreSQL** — relational database
- **Spring Security** — authentication & authorization
- **JWT** (`jjwt`) — stateless, token-based authentication
- **BCrypt** — password hashing
- **Maven** — build/dependency management
- **Postman** — manual API testing throughout development
- **Redis** — optional caching layer for future performance improvements

## Architecture

Mercury follows a **package-by-feature** structure rather than package-by-layer — each business domain (`customer`, `product`, `cart`, `order`, `payment`, `auth`, `security`, `exception`) owns its own entity, repository, service, controller, DTOs, and mapper, rather than grouping all controllers together, all services together, etc. This keeps related code co-located and scales better as the domain grows.

Each feature follows a consistent internal layering:

```
Controller  → handles HTTP request/response only
Service     → business logic
Repository  → data access (Spring Data JPA)
Mapper      → converts between Entities and DTOs
DTOs        → define what the API actually accepts/returns (entities are never exposed directly)
```

### Key design decisions

- **Entities are never exposed over the API.** Every endpoint uses request/response DTOs, mapped via static mapper classes, so internal fields (like `password`) and database-only concerns never leak to clients.
- **Checkout is fully transactional.** Placing an order (`OrderService.placeOrder`) converts cart items into order items, reduces stock, and clears the cart — all wrapped in `@Transactional`, so a failure partway through (e.g., insufficient stock on one item) rolls back everything, leaving no partial/corrupted state.
- **`CascadeType.PERSIST`** on the `Order → OrderItem` relationship lets saving an `Order` automatically persist its `OrderItem`s in the correct sequence, avoiding foreign-key-before-parent-exists bugs.
- **Prices are frozen at time of purchase.** `OrderItem.priceAtPurchase` stores the unit price at the moment of the order, independent of any future changes to `Product.price` — so historical orders/receipts stay accurate.
- **Passwords are never stored in plain text.** Registration hashes passwords with `BCryptPasswordEncoder` before persisting; login compares hashes, never raw values.
- **Authentication is fully stateless**, via JWT. On login, a signed token (containing `customerId`, `email`, and `role` as claims) is issued. A custom `JwtAuthFilter` validates this token on every subsequent request and populates Spring's `SecurityContext` — no server-side session storage.
- **Identity is never trusted from the client.** Endpoints like `/cart`, `/orders`, and payment processing pull the acting customer's ID from the authenticated JWT principal (`@AuthenticationPrincipal`), not from a URL path variable — preventing one customer from acting on another customer's data by simply changing an ID in the request.
- **Role-based authorization** (`CUSTOMER` / `ADMIN`) restricts sensitive endpoints (e.g. viewing the full customer list) to admins only. New registrations can never self-assign a role — it's always hardcoded server-side.
- **Money is handled with `BigDecimal`**, never `double`/`float`, to avoid floating-point rounding errors in prices and totals.

## Features

- **Customer** — registration (with hashed passwords), profile retrieval, admin-only full customer list
- **Product** — CRUD, stock management with insufficient-stock protection
- **Cart** — add-to-cart with automatic quantity merging for duplicate items, live cart view with calculated total
- **Order** — checkout from cart with atomic stock reduction, order history per customer
- **Payment** — payment processing against an order, with amount-matching validation, double-payment prevention, and order-ownership verification
- **Auth** — registration, login issuing a JWT, stateless request authentication

## Example Endpoints

| Method | Endpoint | Access |
|---|---|---|
| POST | `/customers` | Public |
| POST | `/auth/login` | Public |
| GET | `/products` | Public |
| GET | `/customers` | Authenticated (self) |
| GET | `/customers/all` | Admin only |
| POST | `/cart/add` | Authenticated |
| GET | `/cart` | Authenticated |
| POST | `/orders/place` | Authenticated |
| GET | `/orders` | Authenticated |
| POST | `/payments/{orderId}/pay` | Authenticated (order owner only) |

## Running Locally

1. Ensure PostgreSQL is running and a database named `mercury` exists.
2. Update `application.properties` with your local database credentials.
3. Set a `jwt.secret` value in `application.properties` (a long random string).
4. Run the application — Hibernate (`ddl-auto=update`) will create the schema automatically.
5. Use Postman (or any HTTP client) to interact with the API, starting with `POST /customers` to register and `POST /auth/login` to get a token.

## Challenges Solved Along the Way

- **Save-ordering bug**: `OrderItem`s were being saved before their parent `Order` existed in the database, leaving them with an invalid foreign key. Solved with `CascadeType.PERSIST`.
- **Reserved SQL keyword collision**: `order` is a reserved word in PostgreSQL, breaking table/column generation. Solved with `@Table(name = "orders")` and explicit `@JoinColumn` naming.
- **Silent update loss**: Updating a fetched `Order`'s status inside a service method didn't appear to persist until understanding Hibernate's dirty-checking — managed entities inside an active `@Transactional` method auto-persist field changes at commit, without an explicit `.save()` call.
- **NullPointerException on new Cart objects**: A `@OneToMany` collection field was `null` on newly-constructed entities (Hibernate only manages it as non-null once fetched from the database) — fixed by initializing the field at declaration.
- **Authorization gap**: Endpoints originally trusted a `customerId` path variable supplied by the client — meaning one customer could act as another simply by changing a number in the URL. Fixed by deriving identity exclusively from the authenticated JWT principal instead.

## What's Next

- Automated test coverage (unit + integration)
- Docker containerization
- Cart item removal / quantity update endpoints
- Pagination on product listing
