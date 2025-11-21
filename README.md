# Bookshelf API

A Spring Boot REST API for managing a personal bookshelf collection. This project demonstrates CRUD operations, JPA/Hibernate integration with MySQL, validation, and serving Markdown documentation.

## Features

- **Book Management**: Create, read, update, and delete books
- **Reading Progress Tracking**: Track pages read and completion status
- **Search and Filter**: Find books by name, reading status, or completion
- **Data Validation**: Input validation using Jakarta Bean Validation
- **Markdown Documentation**: Serve API documentation as HTML or raw Markdown
- **MySQL Integration**: Persistent storage with JPA/Hibernate

## Tech Stack

- **Java 17**
- **Spring Boot 3.4.x**
  - Spring Web (REST API)
  - Spring Data JPA (Database access)
  - Spring Validation (Input validation)
- **MySQL 8.0+** (Database)
- **Lombok** (Reduce boilerplate code)
- **Flexmark** (Markdown to HTML conversion)
- **Maven** (Build tool)

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven 3.6+ (or use the included Maven Wrapper)
- MySQL 8.0+ database server
- IDE (IntelliJ IDEA recommended for Lombok support)

## Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/ramdany05/practice-springboot-bookshelfAPI.git
cd practice-springboot-bookshelfAPI
```

### 2. Setup Database

Create a MySQL database:

```sql
CREATE DATABASE bookshelf_api;
```

Configure database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookshelf_api?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build and Run

**Using Maven Wrapper (Windows):**
```bash
.\mvnw.cmd spring-boot:run
```

**Using Maven Wrapper (Linux/Mac):**
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

**Using Maven:**
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Test the API

You can use tools like curl, Postman, or your browser to interact with the API:

```bash
# Get all books
curl http://localhost:8080/books

# View markdown documentation as HTML
curl http://localhost:8080/docs/intro
```

## API Endpoints

### Book Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/books` | Get all books (supports filtering) |
| GET | `/books/{id}` | Get a specific book by ID |
| POST | `/books` | Add a new book |
| PUT | `/books/{id}` | Update an existing book |
| DELETE | `/books/{id}` | Delete a book |

#### Query Parameters for GET `/books`

- `name`: Filter by book name (case-insensitive)
- `reading`: Filter by reading status (true/false)
- `finished`: Filter by completion status (true/false)

### Example Request (Add a Book)

```bash
POST /books
Content-Type: application/json

{
  "name": "The Pragmatic Programmer",
  "year": 2019,
  "author": "David Thomas, Andrew Hunt",
  "summary": "Your journey to mastery",
  "publisher": "Addison-Wesley Professional",
  "pageCount": 352,
  "readPage": 100,
  "reading": true
}
```

## Markdown Documentation Feature

This project includes a built-in feature to serve Markdown files as HTML or raw Markdown.

### Markdown Endpoints

| Method | Endpoint | Description | Content-Type |
|--------|----------|-------------|--------------|
| GET | `/docs/{name}` | Get Markdown rendered as HTML | `text/html` |
| GET | `/docs/raw/{name}` | Get raw Markdown | `text/markdown` |

### How It Works

1. **Place Markdown files** in `src/main/resources/markdown/`
2. **Access rendered HTML** at `http://localhost:8080/docs/{filename}` (without `.md` extension)
3. **Access raw Markdown** at `http://localhost:8080/docs/raw/{filename}`

### Example Markdown Files

The project includes sample documentation:

- **Intro**: `http://localhost:8080/docs/intro` - API introduction and overview
- **Setup**: `http://localhost:8080/docs/setup` - Detailed setup instructions

### Adding Your Own Markdown Files

1. Create a new `.md` file in `src/main/resources/markdown/`
2. Example: `src/main/resources/markdown/api-guide.md`
3. Access it at: `http://localhost:8080/docs/api-guide`

### Serving Static Markdown (Alternative)

If you want to serve Markdown files directly without HTML conversion:

1. Place `.md` files in `src/main/resources/static/`
2. Access them at: `http://localhost:8080/filename.md`

**Note**: Static files are served as-is without conversion to HTML.

### Flexmark Dependency

Markdown to HTML conversion is powered by Flexmark. The dependency is already included in `pom.xml`:

```xml
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>0.64.8</version>
</dependency>
```

## Project Structure

```
src/
├── main/
│   ├── java/com/ramdany/bookshelfAPI/
│   │   ├── BookshelfApiApplication.java    # Main application class
│   │   ├── controller/
│   │   │   └── MarkdownController.java     # Markdown rendering endpoints
│   │   ├── dto/
│   │   │   ├── BookRequest.java            # Request DTO for creating/updating books
│   │   │   ├── BookDetailResponse.java     # Detailed book response DTO
│   │   │   └── BookListItemResponse.java   # List item response DTO
│   │   ├── model/
│   │   │   └── Book.java                   # Book entity
│   │   └── repository/
│   │       └── BookRepository.java         # JPA repository
│   └── resources/
│       ├── application.properties          # Application configuration
│       ├── markdown/                       # Markdown documentation files
│       │   ├── intro.md                    # API introduction
│       │   └── setup.md                    # Setup guide
│       └── static/                         # Static resources (optional)
└── test/
    └── java/com/ramdany/bookshelfAPI/
        └── BookshelfApiApplicationTests.java
```

## Building for Production

### Create JAR Package

```bash
mvn clean package
```

The executable JAR will be created at `target/bookshelfAPI-0.0.1-SNAPSHOT.jar`

### Run the JAR

```bash
java -jar target/bookshelfAPI-0.0.1-SNAPSHOT.jar
```

### Run Tests

```bash
mvn test
```

## Development Tips

### Lombok Setup in IntelliJ IDEA

1. Install the Lombok plugin: **File → Settings → Plugins → Search "Lombok"**
2. Enable annotation processing: **File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
3. Check "Enable annotation processing"

### Spring Boot DevTools (Optional)

To enable hot-reload during development, add this dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Database Schema Auto-Creation

The application is configured with `spring.jpa.hibernate.ddl-auto=update`, which means:
- Tables are created automatically on first run
- Schema changes are applied automatically (use with caution in production!)

For production, consider using:
```properties
spring.jpa.hibernate.ddl-auto=validate
```

### Connection Pooling

HikariCP connection pooling is pre-configured in `application.properties`:
- Maximum pool size: 10
- Minimum idle connections: 2
- Connection timeout: 30 seconds

## Troubleshooting

### Port 8080 Already in Use

Change the port in `application.properties`:

```properties
server.port=8081
```

### MySQL Connection Failed

- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure `bookshelf_api` database exists
- Test connection: `mysql -u root -p`

### Lombok Not Working

- Install Lombok plugin in your IDE
- Enable annotation processing
- Restart IDE after configuration

### Maven Build Issues

Force update dependencies:

```bash
mvn clean install -U
```

### Cannot Find Markdown Files (404 Error)

- Verify `.md` files are in `src/main/resources/markdown/`
- Check file names (case-sensitive on Linux)
- Rebuild the project: `mvn clean package`

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit your changes: `git commit -am 'Add feature'`
4. Push to the branch: `git push origin feature-name`
5. Submit a pull request

## License

This is a practice project for learning Spring Boot.

## Author

**ramdany05**

## Acknowledgments

- Spring Boot team for excellent framework and documentation
- Flexmark library for Markdown processing
- Lombok for reducing boilerplate code
