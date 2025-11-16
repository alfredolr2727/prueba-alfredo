# Prueba Técnica - Spring Boot API

A Spring Boot REST API demonstrating integration with PostgreSQL and Redis, built as a technical assessment. The application provides two endpoints for managing charging station data, each utilizing a different persistence layer.

## 📋 Overview

This project implements a REST API with two main endpoints:
- **`/pool-find`** - Searches and filters charging pools using **Redis** for fast data access
- **`/availability/charge-points/`** - Retrieves dynamic status of charge points using **PostgreSQL**

## 🛠️ Technologies

- **Spring Boot** 3.5.7
- **Java** 21
- **PostgreSQL** 15
- **Redis** 7
- **Maven** for dependency management
- **Docker & Docker Compose** for containerization
- **Lombok** for reducing boilerplate code

## 📦 Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose

## 🚀 Getting Started

### 1. Build the Application

Generate the JAR file:

```bash
mvn clean install
```

This will create `prueba-0.0.1-SNAPSHOT.jar` in the `target/` directory.

### 2. Start the Environment

Launch all components (application, PostgreSQL, and Redis) using Docker Compose:

```bash
docker compose up
```

This single command will:
- Build the Spring Boot application Docker image
- Start a **PostgreSQL 15** container with initialization scripts
- Start a **Redis 7** container for caching
- Start the **Spring Boot application** container
- Expose the application on port `8080`
- Expose PostgreSQL on port `5432`
- Expose Redis on port `6379`

The application will automatically wait for PostgreSQL and Redis to be healthy before starting.

### 3. Access the Application

The API will be available at:

```
http://localhost:8080
```

## 📚 API Documentation

### Collections and Example Requests

The project includes ready-to-use API request collections in the **`collections/`** directory:

#### 📦 Postman Collection

A complete Postman collection is available for import with all endpoints pre-configured:

**File:** `collections/Data_Controller_API.postman_collection.json`

**To use:**
1. Open Postman
2. Click **Import** button
3. Select the file `collections/Data_Controller_API.postman_collection.json`
4. The collection will be imported with pre-configured requests and environment variables
5. All requests include the required `Subscription-Key` header

#### 📄 HTTP Request Files

For developers using IntelliJ IDEA HTTP Client or VS Code REST Client extension, example HTTP request files are provided:

- **`collections/api-charge-point-http-requests.http`** - Examples for Charge Point availability endpoint
- **`collections/api-poll-http-requests.http`** - Examples for Pool search endpoint

These files can be executed directly from your IDE to test the API endpoints.

### Authentication

Both endpoints require a subscription key header:
```
Subscription-Key: 1234ABCD
```

### Endpoints

#### 1. Pool Search (Redis)
```http
POST /pool-find
Content-Type: application/json
Subscription-Key: 1234ABCD

{
  "poolIds": ["pool-1", "pool-2"],
  "filters": { ... }
}
```

#### 2. Charge Points Dynamic Status (PostgreSQL)
```http
POST /availability/charge-points/
Content-Type: application/json
Subscription-Key: 1234ABCD

{
  "chargePointIds": ["cp-001", "cp-002"]
}
```

### Example Requests

#### Postman Collection

A ready-to-use Postman collection is available for import:

📦 **`collections/Data_Controller_API.postman_collection.json`**

To use it:
1. Open Postman
2. Click **Import**
3. Select the file `collections/Data_Controller_API.postman_collection.json`
4. The collection will be imported with pre-configured requests and environment variables

#### HTTP Files

Example HTTP requests are also available in the `collections/` directory for use with IntelliJ IDEA HTTP Client or REST Client extensions:

- **`collections/api-charge-point-http-requests.http`** - Charge point endpoint examples
- **`collections/api-poll-http-requests.http`** - Pool search endpoint examples

### Swagger Documentation

OpenAPI specification is available in `java-spring-boot-test-swagger.yaml`.

## 🗄️ Test Data

The application comes pre-loaded with test data for development and testing purposes:

### PostgreSQL
Sample records are automatically loaded via initialization scripts located in `docker/docker/db-init/`:
- **`01-schema.sql`** - Database schema creation
- **`02-data.sql`** - Sample charge point data

These scripts are executed automatically when the PostgreSQL Docker container starts for the first time.

### Redis
Test data for pools is loaded at application startup through a `CommandLineRunner` implementation in the codebase.

## 📁 Project Structure

```
src/
├── main/java/com/jember/alfredo/prueba/
│   ├── PruebaApplication.java      # Main application class
│   ├── DatabaseValidator.java      # PostgreSQL connection validator
│   ├── RedisValidator.java         # Redis connection validator
│   ├── controller/                 # REST API endpoints
│   │   └── DataController.java     # Main controller with both endpoints
│   ├── service/                    # Business logic layer
│   │   ├── ChargePointService.java # PostgreSQL-based service
│   │   └── PoolService.java        # Redis-based service
│   ├── repository/                 # Data access layer
│   ├── dto/                        # Data transfer objects
│   ├── mapper/                     # Object mapping utilities
│   └── cache/                      # Redis configuration
└── test/                           # Unit and integration tests
```

## 🔧 Configuration

The application can be configured via environment variables or `application.yml`:

### Database Configuration
- `SPRING_DATASOURCE_URL` - PostgreSQL connection URL (default: `jdbc:postgresql://localhost:5432/mydb`)
- `SPRING_DATASOURCE_USERNAME` - Database username (default: `myuser`)
- `SPRING_DATASOURCE_PASSWORD` - Database password (default: `mypassword`)

### Redis Configuration
- `SPRING_REDIS_HOST` - Redis host (default: `localhost`)
- `SPRING_REDIS_PORT` - Redis port (default: `6379`)

## 🛑 Stopping the Environment

To stop all running Docker containers:

```bash
docker compose down
```

To stop and remove volumes (this will delete all data):

```bash
docker compose down -v
```

## 📝 Notes

- The application uses a fake subscription key (`1234ABCD`) for authentication purposes
- PostgreSQL data is persisted in a Docker volume named `postgres_data`
- Redis is configured with minimal persistence (save every 60 seconds if at least 1 key changed)
- The application validates database connections on startup using `DatabaseValidator` and `RedisValidator`

## 🧪 Testing

Run the tests with:

```bash
mvn test
```

---

**Author**: Alfredo  
**Purpose**: Technical Assessment  
**Date**: 2025

