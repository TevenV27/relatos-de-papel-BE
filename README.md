# Relatos de Papel - Microservices Base Configuration

**Configuración base** de un sistema de microservicios para gestión de catálogo de libros y procesamiento de pagos, construido con Spring Boot 3.x y Spring Cloud.

## ⚠️ IMPORTANTE

Este proyecto proporciona la **INFRAESTRUCTURA Y CONFIGURACIÓN BASE**. Las capas de negocio (Controllers, Services, Repositories, Models, etc.) **deben ser implementadas por cada equipo**.

Ver **`IMPLEMENTATION_GUIDE.md`** para instrucciones detalladas de implementación.

## Architecture Overview

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────────────┐
│  Cloud Gateway      │  (Port 8080)
│  - Proxy Inverso    │
│  - POST → CRUD      │
└──────┬──────────────┘
       │
       ▼
┌─────────────────────┐
│  Eureka Server      │  (Port 8761)
│  Service Discovery  │
└──────┬──────────────┘
       │
       ├──────────────┬───────────────┐
       ▼              ▼               ▼
┌──────────────┐ ┌──────────────┐   ...
│ ms-books-    │ │ ms-books-    │
│ catalogue    │ │ payments     │
│ (Port 8081)  │ │ (Port 8082)  │
└──────────────┘ └──────────────┘
```

## Services

### 1. Eureka Server (Port 8761) ✅ COMPLETO
- **Purpose**: Service Discovery and Registration
- **URL**: http://localhost:8761
- **Status**: ✅ Completamente configurado
- **Description**: Central registry for all microservices

### 2. Cloud Gateway (Port 8080) ✅ COMPLETO
- **Purpose**: API Gateway
- **URL**: http://localhost:8080
- **Status**: ✅ Rutas configuradas para `/api/books/**` y `/api/payments/**`
- **Features**:
  - Routes all client requests to appropriate microservices
  - Load balancing via Eureka service names

### 3. MS Books Catalogue (Port 8081) ⚠️ POR IMPLEMENTAR
- **Purpose**: Book inventory and catalogue management
- **Service Name**: `ms-books-catalogue`
- **Status**: ⚠️ Solo configuración base - **implementar capas de negocio**
- **TODO**:
  - Crear entidad Book
  - Crear BookRepository, BookService, BookController
  - Implementar CRUD y gestión de stock

### 4. MS Books Payments (Port 8082) ⚠️ POR IMPLEMENTAR
- **Purpose**: Payment processing and order management
- **Service Name**: `ms-books-payments`
- **Status**: ⚠️ Solo configuración base - **implementar capas de negocio**
- **TODO**:
  - Crear entidad Payment
  - Crear PaymentRepository, PaymentService, PaymentController
  - Implementar cliente para comunicación con Catalogue
  - Implementar patrón de resiliencia

## Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.2.2
- **Spring Cloud**: 2023.0.0
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Persistence**: Spring Data JPA
- **Database**: H2 (dev), PostgreSQL/MySQL (prod)
- **Build Tool**: Maven 3.8+

## Prerequisites

- JDK 17 or higher
- Maven 3.8+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

## Quick Start

### 1. Build all modules (solo verifica configuración)
```bash
mvn clean install
```

⚠️ **Nota**: En este punto, los microservicios arrancarán pero **NO tendrán funcionalidad** porque las capas de negocio no están implementadas.

### 2. Start services in order

#### Step 1: Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
Wait for Eureka to be fully started (check http://localhost:8761)

#### Step 2: Start Microservices (in parallel)
```bash
# Terminal 2
cd ms-books-catalogue
mvn spring-boot:run

# Terminal 3
cd ms-books-payments
mvn spring-boot:run
```

#### Step 3: Start Gateway
```bash
cd cloud-gateway
mvn spring-boot:run
```

### 3. Verify infrastructure is running
- ✅ Eureka Dashboard: http://localhost:8761 (debe mostrar 3 servicios registrados)
- ✅ Gateway Health: http://localhost:8080/actuator/health
- ⚠️ Books Catalogue: http://localhost:8080/api/books (devolverá 404 hasta que implementes los endpoints)
- ⚠️ Payments: http://localhost:8080/api/payments (devolverá 404 hasta que implementes los endpoints)

## Gateway Proxy Inverso Pattern

The Cloud Gateway implements a special "Proxy Inverso" pattern that transcribes POST requests into appropriate CRUD operations:

```
POST /api/books/action
{
  "method": "GET",
  "path": "/books/123"
}
→ Transcribed to: GET http://ms-books-catalogue/books/123

POST /api/books/action
{
  "method": "PUT",
  "path": "/books/123",
  "body": {...}
}
→ Transcribed to: PUT http://ms-books-catalogue/books/123
```

## Inter-Service Communication

Services communicate via **Eureka service names** (no hardcoded IPs/ports):

```java
// Example: ms-books-payments calling ms-books-catalogue
String url = "http://ms-books-catalogue/api/books/" + bookId;
Book book = restTemplate.getForObject(url, Book.class);
```

## Resilience Pattern

`ms-books-payments` validates book availability before processing:

1. Check book exists in catalogue
2. Verify stock availability
3. Process payment
4. Update stock (if needed)

## Configuration Profiles

Each service supports multiple profiles:

- `default`: H2 in-memory database
- `dev`: H2 with persistent storage
- `prod`: PostgreSQL/MySQL

Activate profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## API Endpoints

### Books Catalogue (via Gateway)
```
GET    /api/books           - List all books
GET    /api/books/{id}      - Get book by ID
POST   /api/books           - Create new book
PUT    /api/books/{id}      - Update book
DELETE /api/books/{id}      - Delete book
GET    /api/books/stock/{id} - Check stock
```

### Payments (via Gateway)
```
GET    /api/payments        - List all payments
GET    /api/payments/{id}   - Get payment by ID
POST   /api/payments        - Process new payment
GET    /api/payments/book/{bookId} - Get payments for a book
```

## Development Guidelines

### Adding a New Microservice

1. Create new module in parent `pom.xml`
2. Add Eureka client dependency
3. Configure `application.yml` with unique service name and port
4. Implement business logic
5. Register routes in Cloud Gateway

### Database Configuration

**H2 Console** (development):
- URL: http://localhost:{port}/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

### Troubleshooting

**Service not registering with Eureka:**
- Check Eureka Server is running on port 8761
- Verify `eureka.client.service-url.defaultZone` in application.yml
- Check network connectivity

**Gateway routing issues:**
- Verify service name matches exactly in Eureka
- Check gateway routes configuration
- Review gateway logs for routing errors

**Inter-service communication fails:**
- Ensure both services are registered in Eureka
- Use `@LoadBalanced` RestTemplate
- Check service names (case-sensitive)

## Project Structure

```
relatos-de-papel-parent/
├── pom.xml                          # Parent POM
├── README.md                        # This file
├── .gitignore
│
├── eureka-server/                   # Service Discovery
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
│               └── application.yml
│
├── cloud-gateway/                   # API Gateway
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
│               └── application.yml
│
├── ms-books-catalogue/             # Books Microservice
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           └── resources/
│               └── application.yml
│
└── ms-books-payments/              # Payments Microservice
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            └── resources/
                └── application.yml
```

## Contributing

1. Follow Spring Boot best practices
2. Maintain service independence
3. Use Eureka service names (never hardcode ports)
4. Implement proper error handling and resilience
5. Write meaningful commit messages

## License

This project is part of the "Relatos de Papel" educational initiative.

## Contact

For questions or support, please contact the development team.
