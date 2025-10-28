# Neighbourhood Watch API

A comprehensive Spring Boot REST API for managing a neighbourhood watch system. The system includes houses, members, patrols, subscriptions, payments, notifications, SOS alerts, and audit logs. Built for ease of integration, testing, and expansion.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Testing with .http Files](#testing-with-http-files)
- [License](#license)

---

## Features

- **House Management**: CRUD for houses.
- **Member Management**: Manage users and house assignments.
- **Patrols**: Record patrols and anomalies at checkpoints.
- **Checkpoints**: Manage checkpoints associated with houses or locations.
- **Subscriptions**: Monthly and annual subscription management.
- **Payments**: Record payments with mobile money, card, or cash.
- **Notifications**: Send info, alerts, and reminders to users.
- **SOS Alerts**: Emergency alerts with geo-location and resolution tracking.
- **Audit Logs**: Track all important actions with user and system events.
- **DTO-based API Responses**: Avoid lazy-loading issues and optimize JSON responses.

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- PostgreSQL (or any relational DB)
- Lombok
- Maven
- REST API
- JSON

---

## Project Structure

```

src/main/java/com/neighbourhoodwatch/api/
│
├── house/          # House entity, repository, service, controller, DTOs
├── user/           # User entity, repository, service, controller, DTOs
├── checkpoint/     # Checkpoints
├── patrol/         # Patrols
├── subscription/   # Subscriptions
├── payment/        # Payments
├── notification/   # Notifications
├── sos/            # SOS Alerts
└── audit/          # Audit Logs

````

- Each module follows the same pattern:
  - **Entity**: JPA entity with annotations.
  - **Repository**: Spring Data JPA repository.
  - **DTO**: To prevent lazy-loading and simplify JSON responses.
  - **Service**: Business logic layer.
  - **Controller**: REST endpoints.

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL or MySQL (update `application.properties`)
- IDE (IntelliJ, Eclipse, VS Code)

### Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/neighbourhood-watch-api.git
cd neighbourhood-watch-api
````

2. Configure database in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/neighbourhood_watch
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

3. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## API Endpoints

| Module       | Base URL             | Description                   |
| ------------ | -------------------- | ----------------------------- |
| House        | `/api/houses`        | CRUD operations for houses    |
| User         | `/api/users`         | Manage members/users          |
| Checkpoint   | `/api/checkpoints`   | Manage checkpoints            |
| Patrol       | `/api/patrols`       | Record patrols at checkpoints |
| Subscription | `/api/subscriptions` | Manage subscriptions          |
| Payment      | `/api/payments`      | Record payment transactions   |
| Notification | `/api/notifications` | Send notifications            |
| SOS Alert    | `/api/sosalerts`     | Emergency alerts              |
| Audit Log    | `/api/auditlogs`     | Track system/user actions     |

---

## Testing with `.http` Files

To easily test the API:

1. Open the corresponding `.http` files in your IDE (VS Code with REST Client extension recommended):

   * `nwa-houses.http`
   * `nwa-patrols.http`
   * `nwa-subscriptions.http`
   * `nwa-payments.http`
   * `nwa-notifications.http`
   * `nwa-sosalerts.http`
   * `nwa-auditlogs.http`
2. Replace any placeholder UUIDs (`{{houseUUID}}`, `{{patrolUUID}}`, etc.) with real IDs from previous requests.
3. Send requests directly from the IDE to verify API functionality.

---

## Notes

* All endpoints return JSON.
* DTOs prevent lazy-loading exceptions when serializing JPA entities.
* Audit logs automatically track important user/system actions.

---

## License

This project is licensed under the MIT License.

---

## Author

Adam Musakabantu Muyobo
Computer Science Student, University of Botswana
