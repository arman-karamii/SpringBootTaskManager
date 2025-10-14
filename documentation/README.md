# Spring Boot Task Management System

A comprehensive Spring Boot application demonstrating MVC architecture with RESTful API for task management.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Quick Start](#quick-start)
- [Documentation](#documentation)
- [API Reference](#api-reference)
- [Testing](#testing)
- [Deployment](#deployment)

## 🚀 Overview

This project is a **Spring Boot Task Management System** that provides a RESTful API for managing tasks. It demonstrates modern Java web development practices using the Model-View-Controller (MVC) architectural pattern.

### Key Information

- **Framework**: Spring Boot 4.0.0-M3
- **Java Version**: 17
- **Build Tool**: Maven 3.9.11
- **Architecture**: MVC (Model-View-Controller)
- **Testing**: JUnit 5 with comprehensive test coverage

## ✨ Features

### Core Functionality
- ✅ **Task Management**: Create, read, update, and delete tasks
- ✅ **RESTful API**: Full CRUD operations via HTTP endpoints
- ✅ **MVC Architecture**: Clean separation of concerns
- ✅ **Test-Driven Development**: Comprehensive unit tests
- ✅ **In-Memory Storage**: Simple data persistence

### Technical Features
- ✅ **Spring Boot Auto-Configuration**: Minimal configuration required
- ✅ **Spring Web MVC**: RESTful web services
- ✅ **Spring Test**: Comprehensive testing framework
- ✅ **Maven Wrapper**: Consistent build environment
- ✅ **JSON Serialization**: Automatic request/response handling

## 🏗️ Architecture

The project follows the **MVC (Model-View-Controller)** architectural pattern:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Controller    │    │     Service    │    │      Model      │
│                 │    │                 │    │                 │
│ TaskController  │───▶│  TaskService    │───▶│     Task       │
│                 │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Layer Responsibilities

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and data operations
- **Model Layer**: Represents data entities and business objects

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.9+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd taskManager
   ```

2. **Build the project**
   ```bash
   ./mvnw clean compile
   ```

3. **Run tests**
   ```bash
   ./mvnw test
   ```

4. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Access the API**
   ```bash
   curl http://localhost:8080/tasks
   ```

## 📚 Documentation

Comprehensive documentation is available in the `documentation/` folder:

### Documentation Files

| File | Description |
|------|-------------|
| [`01-project-overview.qmd`](01-project-overview.qmd) | Project overview and structure |
| [`02-mvc-architecture.qmd`](02-mvc-architecture.qmd) | MVC architecture explanation |
| [`03-api-documentation.qmd`](03-api-documentation.qmd) | Complete API reference |
| [`04-setup-guide.qmd`](04-setup-guide.qmd) | Installation and setup guide |

### Viewing Documentation

To view the QMD files as HTML:

1. **Install Quarto**
   ```bash
   # macOS
   brew install quarto
   
   # Windows
   # Download from https://quarto.org/docs/get-started/
   ```

2. **Render documentation**
   ```bash
   cd documentation
   quarto render
   ```

## 🔌 API Reference

### Base URL
```
http://localhost:8080
```

### Endpoints

| Method | Endpoint | Description | Status Code |
|--------|----------|-------------|-------------|
| GET | `/tasks` | Get all tasks | 200 OK |
| POST | `/tasks` | Create new task | 201 Created |
| GET | `/tasks/{id}` | Get task by ID | 200 OK / 404 Not Found |
| PUT | `/tasks/{id}` | Update task by ID | 200 OK / 404 Not Found |
| DELETE | `/tasks/{id}` | Delete task by ID | 204 No Content / 404 Not Found |

### Example Usage

```bash
# Get all tasks
curl http://localhost:8080/tasks

# Create a new task
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"New Task","isDone":false}'

# Get task by ID
curl http://localhost:8080/tasks/1

# Update task
curl -X PUT http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Task","isDone":true}'

# Delete task
curl -X DELETE http://localhost:8080/tasks/1
```

## 🧪 Testing

The project implements **Test-Driven Development (TDD)** with comprehensive test coverage:

### Test Structure
- **Unit Tests**: Individual component testing
- **Integration Tests**: End-to-end API testing
- **Test Isolation**: Each test runs independently

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=TaskControllerTest

# Run tests with coverage
./mvnw test jacoco:report
```

### Test Coverage
- ✅ **TaskService**: 7 test methods
- ✅ **TaskController**: 6 test methods
- ✅ **Application**: 1 integration test

## 🚀 Deployment

### Development
```bash
./mvnw spring-boot:run
```

### Production
```bash
# Build JAR
./mvnw package

# Run JAR
java -jar target/taskManager-0.0.1-SNAPSHOT.jar
```

### Docker
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/taskManager-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📁 Project Structure

```
taskManager/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── taskManager/
│   │   │               ├── TaskManagementApplication.java
│   │   │               ├── TaskController.java
│   │   │               ├── controller/
│   │   │               │   └── TaskController.java
│   │   │               ├── model/
│   │   │               │   └── Task.java
│   │   │               └── service/
│   │   │                   └── TaskService.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── taskManager/
│                       ├── TaskManagementApplicationTests.java
│                       ├── controller/
│                       │   └── TaskControllerTest.java
│                       └── service/
│                           └── TaskServiceTest.java
├── target/
├── documentation/
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## 🔧 Configuration

### Application Properties
```properties
# Server configuration
server.port=8080
server.servlet.context-path=/

# Logging configuration
logging.level.com.example.taskManager=INFO
logging.level.org.springframework.web=INFO
```

### Environment Variables
```bash
# Change port
export SERVER_PORT=9090

# Change logging level
export LOGGING_LEVEL_COM_EXAMPLE_TASKMANAGER=DEBUG
```

## 🛠️ Development

### IDE Setup

#### IntelliJ IDEA
1. Open IntelliJ IDEA
2. Import project from `pom.xml`
3. Configure run configuration for `TaskManagementApplication`
4. Run the application

#### VS Code
1. Install Extension Pack for Java
2. Open project folder
3. Run `TaskManagementApplication.java`

### Code Style
- Follow Java naming conventions
- Use meaningful variable names
- Add comments for complex logic
- Maintain consistent formatting

## 🐛 Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   lsof -i :8080
   kill -9 <PID>
   ```

2. **Java Version Mismatch**
   ```bash
   java -version
   # Ensure Java 17+ is installed
   ```

3. **Maven Build Failures**
   ```bash
   ./mvnw clean compile
   ./mvnw dependency:resolve
   ```

## 📈 Future Enhancements

### Planned Features
- [ ] **Database Integration**: JPA/Hibernate
- [ ] **Authentication**: JWT-based authentication
- [ ] **Authorization**: Role-based access control
- [ ] **Pagination**: Paginated task lists
- [ ] **Filtering**: Filter tasks by status, date, etc.
- [ ] **Search**: Full-text search functionality
- [ ] **Validation**: Enhanced input validation
- [ ] **Rate Limiting**: API rate limiting
- [ ] **CORS**: Cross-origin resource sharing
- [ ] **API Versioning**: Versioned API endpoints
- [ ] **OpenAPI/Swagger**: Interactive API documentation

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Armankarami** - *Initial work* - [GitHub](https://github.com/armankarami)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- JUnit team for the testing framework
- Maven team for the build tool
- All contributors and testers

---

*Last updated: 2025-10-13*
