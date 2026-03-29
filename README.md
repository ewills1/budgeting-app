# budgeting-app

A personal finance management platform built as a **microservices** architecture using **Spring Boot**, **MongoDB**, and **Docker**. The application allows users to register, authenticate, and manage their budgets, transactions, and spending categories through a set of independently deployable REST API services.

---

## Table of Contents

- [Architecture Overview](#architecture-overview)
- [Tech Stack](#tech-stack)
- [Services](#services)
  - [User Service](#user-service)
  - [Budget Service](#budget-service)
  - [Transaction Service](#transaction-service)
  - [Category Service](#category-service)
- [API Reference](#api-reference)
- [Docker & Docker Compose](#docker--docker-compose)
- [Getting Started](#getting-started)
- [Makefile Commands](#makefile-commands)
- [Future Plans](#future-plans)

---

## Architecture Overview

```
┌──────────────────────────────────────────────────────────┐
│                    finance-network (Docker bridge)        │
│                                                          │
│  ┌───────────────┐   ┌───────────────┐                  │
│  │  user-service │   │budget-service │                  │
│  │   :8080       │   │   :8081       │                  │
│  └───────────────┘   └───────────────┘                  │
│                                                          │
│  ┌────────────────────┐   ┌──────────────────┐          │
│  │transaction-service │   │category-service  │          │
│  │      :8082         │   │     :8083        │          │
│  └────────────────────┘   └──────────────────┘          │
│                                                          │
│  ┌──────────────┐   ┌──────────────────────┐            │
│  │   MongoDB    │   │   MongoDB Express    │            │
│  │   :27017     │   │       :8086          │            │
│  └──────────────┘   └──────────────────────┘            │
└──────────────────────────────────────────────────────────┘
```

Each service is independently deployable and communicates through the shared Docker bridge network. All services share a single **MongoDB** instance with JWT-based authentication to protect every endpoint.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.1 |
| Build Tool | Maven 3.9.6 (multi-module) |
| Database | MongoDB 7.0 |
| Authentication | JWT (jjwt 0.12.6) + Spring Security |
| Containerisation | Docker + Docker Compose |
| DB Admin UI | MongoDB Express |
| Code Utilities | Lombok |

---

## Services

### User Service

**Port:** `8080`

Handles user registration and authentication. Issues **JWT tokens** that are required by all other services.

### Budget Service

**Port:** `8081`

Manages budgets and the expenses associated with them. All operations are scoped to the authenticated user.

### Transaction Service

**Port:** `8082`

Records and manages individual financial transactions (income or expense entries). All operations are scoped to the authenticated user.

### Category Service

**Port:** `8083`

Manages spending categories that can be used to classify transactions and budget entries. All operations are scoped to the authenticated user.

---

## API Reference

All endpoints (except `/auth/register` and `/auth/login`) require a valid JWT in the `Authorization` header:

```
Authorization: Bearer <token>
```

### User Service — `/auth`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Log in and receive a JWT token |

### Budget Service — `/api/budgets`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/budgets` | Create a new budget |
| `GET` | `/api/budgets` | Get all budgets for the current user |
| `GET` | `/api/budgets/{id}` | Get a specific budget |
| `PUT` | `/api/budgets/{id}` | Update a budget |
| `DELETE` | `/api/budgets/{id}` | Delete a budget |
| `POST` | `/api/budgets/{budgetId}/expenses` | Add an expense to a budget |
| `GET` | `/api/budgets/{budgetId}/expenses` | Get all expenses for a budget |
| `GET` | `/api/budgets/{budgetId}/expenses/{expenseId}` | Get a specific expense |
| `PUT` | `/api/budgets/{budgetId}/expenses/{expenseId}` | Update an expense |
| `DELETE` | `/api/budgets/{budgetId}/expenses/{expenseId}` | Delete an expense |

### Transaction Service — `/api/transactions`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/transactions` | Create a new transaction |
| `GET` | `/api/transactions` | Get all transactions for the current user |
| `GET` | `/api/transactions/{id}` | Get a specific transaction |
| `PUT` | `/api/transactions/{id}` | Update a transaction |
| `DELETE` | `/api/transactions/{id}` | Delete a transaction |

### Category Service — `/api/categories`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/categories` | Create a new category |
| `GET` | `/api/categories` | Get all categories for the current user |
| `GET` | `/api/categories/{id}` | Get a specific category |
| `PUT` | `/api/categories/{id}` | Update a category |
| `DELETE` | `/api/categories/{id}` | Delete a category |

---

## Docker & Docker Compose

The project ships with two Docker Compose configurations.

### Root `docker-compose.yml` — Full Stack

Starts **all** services including the frontend and MongoDB Express GUI.

```
Services started:
  mongodb            :27017   MongoDB 7.0 with authentication
  mongo-express      :8086    MongoDB web admin UI (admin / admin123)
  user-service       :8080
  budget-service     :8081
  transaction-service:8082
  category-service   :8083
  frontend           :5173    React + Vite dev server
```

All services connect through the `finance-network` Docker bridge network.

### `budgeting-app-core/docker-compose.yml` — Backend Only

A simplified compose file that starts only MongoDB and the four backend microservices (no frontend, no MongoDB Express).

### Environment Variables

| Variable | Default | Description |
|---|---|---|
| `SPRING_DATA_MONGODB_URI` | `mongodb://localhost:27017/budgeting` | MongoDB connection string |
| `JWT_SECRET` | *(see application.properties)* | Secret key used to sign JWT tokens |
| `VITE_API_BASE_URL` | `http://localhost:8080/api` | Frontend API base URL |

> **Note:** For production deployments always override `JWT_SECRET` with a strong, randomly generated value.

### Dockerfiles

Each service has its own `Dockerfile` using a two-stage build:

1. **Build stage** — Maven compiles the JAR using `eclipse-temurin:17-jdk`.
2. **Runtime stage** — The JAR is copied into a minimal `eclipse-temurin:17-jre` image.

---

## Getting Started

### Prerequisites

- [Docker](https://docs.docker.com/get-docker/) 24+
- [Docker Compose](https://docs.docker.com/compose/install/) v2+
- [Make](https://www.gnu.org/software/make/) (optional but recommended)
- [Java 17](https://adoptium.net/) + [Maven 3.9+](https://maven.apache.org/) (for running tests and local development)

### Quick Start

```bash
# Clone the repository
git clone https://github.com/ewills1/budgeting-app.git
cd budgeting-app

# Start the full stack (all services + MongoDB + MongoDB Express)
make up

# Or, start only the backend services
make up-services
```

Once running:

| URL | Description |
|---|---|
| `http://localhost:8080` | User Service |
| `http://localhost:8081` | Budget Service |
| `http://localhost:8082` | Transaction Service |
| `http://localhost:8083` | Category Service |
| `http://localhost:5173` | Frontend (full stack only) |
| `http://localhost:8086` | MongoDB Express (admin / admin123) |

---

## Makefile Commands

The `Makefile` at the project root provides shortcuts for all common tasks.

### Full Stack

| Command | Description |
|---|---|
| `make up` | Start all services (full stack) |
| `make down` | Stop all services |
| `make logs` | Stream logs from all services |
| `make status` | Show status of all containers |

### Backend Services Only

| Command | Description |
|---|---|
| `make up-services` | Start MongoDB + four microservices (no frontend) |
| `make down-services` | Stop MongoDB + microservices |

### Build

| Command | Description |
|---|---|
| `make build` | Build all Docker images |
| `make rebuild` | Rebuild images from scratch and start services |

### Individual Service Logs

| Command | Description |
|---|---|
| `make logs-user` | Stream user-service logs |
| `make logs-budget` | Stream budget-service logs |
| `make logs-transaction` | Stream transaction-service logs |
| `make logs-category` | Stream category-service logs |

### Shell Access

| Command | Description |
|---|---|
| `make shell-user` | Open a shell in user-service |
| `make shell-budget` | Open a shell in budget-service |
| `make shell-transaction` | Open a shell in transaction-service |
| `make shell-category` | Open a shell in category-service |
| `make shell-mongo` | Open a `mongosh` shell in MongoDB |

### Testing

| Command | Description |
|---|---|
| `make test` | Run tests in all services (sequential) |
| `make test-user` | Run user-service tests |
| `make test-budget` | Run budget-service tests |
| `make test-transaction` | Run transaction-service tests |
| `make test-category` | Run category-service tests |

### Maintenance

| Command | Description |
|---|---|
| `make clean` | Remove all containers and volumes |
| `make help` | Display all available commands |

---

## Future Plans

### Angular Frontend

The current frontend placeholder uses **React + Vite**. The planned replacement is an **Angular** single-page application that will provide:

- A dashboard showing budget summaries and recent transactions
- Forms for creating and managing budgets, transactions, and categories
- JWT-aware HTTP interceptors to attach the `Authorization` header automatically
- Reactive data binding powered by RxJS observables
- Component-based architecture following Angular best practices

The Angular app will be containerised in the same way as the current frontend — packaged into a Docker image and connected to the existing backend services via Angular's `environment.ts` / `environment.prod.ts` configuration files, with the API base URL (`apiBaseUrl`) injected at build time or via runtime environment variables.

### Kubernetes (K8s)

To make the platform production-ready, the following Kubernetes resources are planned:

- **Deployments** for each microservice with configurable replica counts for horizontal scaling
- **Services** (ClusterIP / LoadBalancer) to expose each microservice internally and externally
- **ConfigMaps** for non-sensitive environment configuration
- **Secrets** for sensitive values such as `JWT_SECRET` and MongoDB credentials
- **Persistent Volume Claims** (PVCs) for MongoDB data durability
- **Ingress** controller (e.g., NGINX) to route external traffic to the correct service
- **Horizontal Pod Autoscaler (HPA)** to automatically scale services under load
- A dedicated **namespace** (`budgeting-app`) to isolate resources

Migration path: the existing Docker Compose files can be converted to Kubernetes manifests using tools like [`kompose`](https://kompose.io/).

### Apache Kafka

To support event-driven communication between microservices, **Apache Kafka** integration is planned:

- **Transaction events** — when a transaction is created or updated, publish an event to a `transactions` topic so that the budget-service can automatically update the remaining budget balance
- **Notification events** — trigger alerts when a user's spending approaches or exceeds a budget limit
- **Audit log** — stream all mutating API calls to a dedicated audit topic for compliance and debugging
- A **Kafka broker** (or managed service such as Confluent Cloud) will be added to the Docker Compose stack alongside **Zookeeper** or KRaft mode for broker coordination
- Spring Boot services will use `spring-kafka` for producer/consumer integration
