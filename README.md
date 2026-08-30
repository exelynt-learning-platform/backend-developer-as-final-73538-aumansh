# Resource Booking System

A RESTful Resource Booking System built with Spring Boot, Java 17+, Spring Security, JWT, and MySQL.

## Features
- JWT Authentication (Login)
- Role-Based Access Control (RBAC) - ADMIN and USER
- ADMIN has full CRUD access to resources and reservations.
- USER has read-only access to resources and can manage their own reservations.
- Reservation statuses: PENDING, CONFIRMED, CANCELLED
- Filtering, Pagination, and Sorting for reservations.
- Validation and error handling.
- Swagger API Documentation.

## Prerequisites
- Java 17+
- Maven 3.6+
- MySQL (or PostgreSQL)

## Setup Instructions

1. **Clone the repository:**
   `ash
   git clone <repository_url>
   cd backend-developer-as-final-73538-aumansh
   `

2. **Database Configuration:**
   By default, the application is configured to connect to a MySQL database named booking_db at localhost:3306 using the username 
oot and password password. The database will be created automatically if it doesn't exist.

   You can override these settings using environment variables:

   - DB_URL : The JDBC URL (e.g., jdbc:mysql://localhost:3306/your_db?createDatabaseIfNotExist=true)
   - DB_USERNAME : Your database username
   - DB_PASSWORD : Your database password
   - JWT_SECRET : Your JWT secret key (must be at least 256 bits/32 bytes)

3. **Run the Application:**
   `ash
   ./mvnw spring-boot:run
   `

## Seed Users
**WARNING: Strongly recommend changing these defaults immediately after the first login.**
Upon startup, the application creates two seed users for testing:
- **Admin User**: admin / (Set via SEED_ADMIN_PASSWORD env variable)
- **Standard User**: user / (Set via SEED_USER_PASSWORD env variable)

## API Documentation
Once the application is running, you can access the Swagger UI documentation at:
- http://localhost:8080/swagger-ui/index.html

## Postman Collection
Alternatively, you can interact with the API endpoints using tools like Postman. Remember to authenticate via /auth/login and include the generated token in the Authorization header as Bearer <token> for protected endpoints.
