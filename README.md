# InternHub Backend

Spring Boot REST API for managing internship applications and connecting students with companies.

## 🚀 Features

- 🔐 **Authentication**: JWT-based, email verification, password reset
- 👥 **User Roles**: Students, Companies, Admins
- 📄 **Internship Management**: Post, search, apply, and track status
- 📬 **Email Notifications**: Application updates and account actions
- 📁 **File Upload**: CV/document support
- 🔍 **Search & Filter**: Advanced query and pagination
- 🛠️ **Admin Tools:** Dashboard with platform stats, user moderation, and flag verification

- 📖 **Swagger Docs**: Auto-generated API docs

## 🛠 Tech Stack

- **Java 17**, **Spring Boot 3.4.4**, **Spring Security**, **PostgreSQL**
- **JWT**, **BCrypt**, **JJWT**, **Lombok**, **MapStruct**
- **SpringDoc OpenAPI**, **Thymeleaf**, **Spring Mail**

## ⚙️ Setup

### Prerequisites

- **Java 17+**
- **Docker & Docker Compose**
- **Maven** (or use included wrapper)

### Quick Start

1. **Clone the repository:**

```bash
git clone <repo>
cd InternHub_backend
```

2. **Create environment file:**

```bash
cp .env.example .env
```

3. **Configure environment variables in `.env`:**

```env
# Database
DATABASE_PASSWORD=your-secure-password

# JWT
JWT_SECRET=your-256-bit-secret-here

# Email Configuration
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Frontend URL
FRONTEND_URL=http://localhost:3000
```

4. **Start database services:**

```bash
docker-compose up -d
```

5. **Run the application:**

```bash
./mvnw spring-boot:run
```

6. **Access services:**
   - Backend API: `http://localhost:8080`
   - Database Admin (Adminer): `http://localhost:8888`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

### Environment Variables

| Variable            | Description                  | Default                                     |
| ------------------- | ---------------------------- | ------------------------------------------- |
| `DATABASE_URL`      | PostgreSQL connection URL    | `jdbc:postgresql://localhost:5432/postgres` |
| `DATABASE_USERNAME` | Database username            | `postgres`                                  |
| `DATABASE_PASSWORD` | Database password            | `changemeinprod!`                           |
| `JWT_SECRET`        | JWT signing secret (256-bit) | Required for production                     |
| `MAIL_HOST`         | SMTP server host             | `smtp.gmail.com`                            |
| `MAIL_PORT`         | SMTP server port             | `587`                                       |
| `MAIL_USERNAME`     | Email username               | Required                                    |
| `MAIL_PASSWORD`     | Email app password           | Required                                    |
| `FRONTEND_URL`      | Frontend application URL     | `http://localhost:3000`                     |

### Database Access via Adminer

- URL: `http://localhost:8888`
- System: PostgreSQL
- Server: `db`
- Username: `postgres`
- Password: Use your `DATABASE_PASSWORD` from `.env`

## 🔧 Configuration

### Email Setup (Gmail)

1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password: Google Account → Security → App passwords
3. Use the app password as `MAIL_PASSWORD` in your `.env` file

### JWT Secret Generation

Generate a secure 256-bit secret:

```bash
# Using OpenSSL
openssl rand -base64 32

# Using Node.js
node -e "console.log(require('crypto').randomBytes(32).toString('base64'))"
```

## 🌐 API Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Ensure all tests pass
5. Submit a pull request

## 📝 License

MIT License - see `LICENSE` file for details
