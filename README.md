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
   `bash
   git clone <repository_url>
   cd backend-developer-as-final-73538-aumansh
   `

2. **Database Configuration:**
   By default, the application connects to MySQL using `springuser` / `springpass`.
   You MUST override these settings using environment variables in production:

   - DB_URL : The JDBC URL (e.g., jdbc:mysql://localhost:3306/booking_db?createDatabaseIfNotExist=true)
   - DB_USERNAME : Your database username
   - DB_PASSWORD : Your database password
   - JWT_SECRET : Your JWT secret key (must be at least 256 bits/32 bytes)

3. **Run the Application:**
   `bash
   ./mvnw spring-boot:run
   `

## Seed Users
Upon startup, the application creates two seed users for testing:
- **Admin User**: Username is `admin`
- **Standard User**: Username is `user`

If SEED_ADMIN_PASSWORD and SEED_USER_PASSWORD are not set in the environment, the application will generate secure random passwords for them and print a warning in the logs. Please check the logs or set the environment variables.

## API Documentation
Once the application is running, you can access the Swagger UI documentation at:
- http://localhost:8080/swagger-ui/index.html
