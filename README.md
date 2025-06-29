# InternHub Backend

Spring Boot REST API for managing internship applications and connecting students with companies.

## 🚀 Features

* 🔐 **Authentication**: JWT-based, email verification, password reset
* 👥 **User Roles**: Students, Companies, Admins
* 📄 **Internship Management**: Post, search, apply, and track status
* 📬 **Email Notifications**: Application updates and account actions
* 📁 **File Upload**: CV/document support
* 🔍 **Search & Filter**: Advanced query and pagination
* 🛠️ **Admin Tools**: User flagging, certificate tracking
* 📖 **Swagger Docs**: Auto-generated API docs

## 🛠 Tech Stack

* **Java 17**, **Spring Boot 3.4.4**, **Spring Security**, **PostgreSQL**
* **JWT**, **BCrypt**, **JJWT**, **Lombok**, **MapStruct**
* **SpringDoc OpenAPI**, **Thymeleaf**, **Spring Mail**

## ⚙️ Setup

### Prerequisites

* Java 17+, Maven 3.6+, PostgreSQL 12+, Docker (optional)

### Run with Docker (Recommended)

```bash
git clone <repo>
cd InternHub_backend
docker-compose up -d
./mvnw spring-boot:run
```

### Manual Setup

1. Create `postgres` DB and configure `application.properties`
2. Add email credentials
3. Build and run:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

## 🌐 API Docs

* Swagger UI: `http://localhost:8080/swagger-ui.html`
* OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🔧 Config Snippets

**JWT**

```properties
jwt.secret=your-secret
jwt.expiry.ms=86400000
```

**Email**

```properties
spring.mail.username=your-email
spring.mail.password=your-app-password
```

**Database**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
```

## 🤝 Contribute

* Fork > Branch > Commit > PR

## 📝 License

MIT – see `LICENSE`
