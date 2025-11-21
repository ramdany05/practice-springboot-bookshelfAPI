# Setup Guide

## Prerequisites

Before running the Bookshelf API, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
- **Maven 3.6+** (or use the included Maven Wrapper)
- **MySQL 8.0+** database server

## Database Setup

### 1. Create Database

Connect to your MySQL server and create the database:

```sql
CREATE DATABASE bookshelf_api;
```

### 2. Configure Database Connection

Edit the `src/main/resources/application.properties` file with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookshelf_api?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

**Note**: The default configuration uses:
- Host: `localhost`
- Port: `3306`
- Database: `bookshelf_api`
- Username: `root`
- Password: (empty by default)

### 3. Database Schema

The application uses Hibernate with `ddl-auto=update`, so the `books` table will be created automatically when you first run the application.

## Build and Run

### Using Maven Wrapper (Recommended)

**On Windows:**
```bash
.\mvnw.cmd spring-boot:run
```

**On Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

### Build JAR Package

```bash
mvn clean package
```

The JAR file will be created in the `target/` directory.

### Run the JAR

```bash
java -jar target/bookshelfAPI-0.0.1-SNAPSHOT.jar
```

## Verify Installation

Once the application is running, you can verify it by:

1. Checking the console output for successful startup messages
2. Accessing the API at: `http://localhost:8080/books`
3. Viewing markdown documentation at: `http://localhost:8080/docs/intro`

## Troubleshooting

### Port Already in Use

If port 8080 is already in use, you can change it in `application.properties`:

```properties
server.port=8081
```

### Database Connection Issues

- Verify MySQL is running: `sudo service mysql status` (Linux) or check Services (Windows)
- Check database credentials in `application.properties`
- Ensure the `bookshelf_api` database exists
- Check firewall settings if connecting to remote database

### Build Failures

If you encounter dependency resolution issues:
```bash
mvn clean install -U
```

This will force update all dependencies.
