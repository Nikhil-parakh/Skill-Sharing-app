# 📚 Skill-Share Scheduler — Backend API

**Skill-Share Scheduler** *is a robust role-based scheduling API built with Spring Boot. It facilitates connections between Mentors and Mentees through secure user management, availability slot creation, and booking workflows.*
---
## 🛠️ Tech Stack

| Layer         | Technology                                |
|---------------|-------------------------------------------|
| Backend       | Java, Spring Boot                         |
| Security      | Spring Security, JWT                      |
| Database      | MySQL                                     |
| ORM           | Spring Data JPA                           |
| Password Hash | BCrypt                                    |
| Build Tool    | Maven                                     |


---

## 🚀 Key Features

* **Secure Authentication:** Stateless JWT-based login and registration.
* **RBAC (Role-Based Access Control):** Custom permissions for `ADMIN`, `MENTOR`, and `MENTEE`.
* **Mentor Workflow:** Create, manage, and delete available time slots.
* **Mentee Workflow:** Browse available slots, book sessions, and manage personal bookings.
* **Admin Dashboard:** Full visibility of users and system-wide management.
* **Global Exception Handling:** Centralized error responses for a consistent API experience.

---

## 🔐 Authentication Flow

1.  **Registration:** User registers; password is encrypted using **BCrypt** before database storage.
2.  **Login:** User authenticates; server generates and returns a **JWT Token**.
3.  **Authorization:** Client includes the token in the `Authorization: Bearer <token>` header for subsequent requests.
4.  **Verification:** A custom **JWT Filter** intercepts requests, validates the token, and sets the security context.



---
## 📂 Project Structure

```text
src
├── main
│   ├── java/com/example/Skills_Share_Scheduler
│   │   ├── Config       # Security, JWT & Bean Definitions
│   │   ├── Controller   # REST API Endpoints
│   │   ├── Entity       # JPA Models (User, Slot, Booking)
│   │   ├── Exceptions   # @RestControllerAdvice & Custom Exceptions
│   │   ├── Repository   # Data Access Layer (JPA)
│   │   └── Service      # Business Logic & Implementation
│   └── resources
│       ├── application.properties
│       └── db/migration # SQL Scripts
└── pom.xml
```
---

## 🚨 Security & Authentication

This API uses **JWT (JSON Web Tokens)** for stateless authentication:

1. User logs in with valid credentials  
2. Backend issues a signed JWT with role info  
3. Frontend sends token in:
**Authorization: Bearer 'token'**
4. A custom JWT filter validates the token on each request  
5. Spring Security enforces endpoint access based on roles

Passwords are encrypted using **BCrypt**.

---

## 📋 API Endpoints

### 🔓 Public (No Token Required)

| Method | Endpoint           | Description              |
|--------|-------------------|--------------------------|
| POST   | `/User/Register`  | Register new user        |
| POST   | `/User/Login`     | Login & receive JWT      |

---

### 👤 User Routes (Authenticated)

| Method | Endpoint                    | Access / Description            |
|--------|-----------------------------|---------------------------------|
| GET    | `/User/User_Id/{id}`        | Get user by ID                  |
| GET    | `/User/Username/{username}` | Get user by username            |
| PUT    | `/User/Update/{id}`         | Update user (self or admin)     |
| DELETE | `/User/Delete/{id}`         | Delete user (admin/self)        |
| GET    | `/User/All_Users`           | List all users (admin only)     |

---

### ⏱️ Slot Management

| Method | Endpoint                  | Description                         |
|--------|---------------------------|-------------------------------------|
| POST   | `/Slot/Create`            | Mentor creates a slot               |
| GET    | `/Slot/MySlots`           | Mentors retrieves own slots         |
| GET    | `/Slot/OfMentor/{id}`     | List available slots by mentor      |
| DELETE | `/Slot/Delete/{slotId}`   | Delete a slot (enforced rules)      |

---

### 📅 Booking

| Method | Endpoint                        | Description                        |
|--------|----------------------------------|------------------------------------|
| POST   | `/Booking/Create/{slotId}`       | Mentee books a slot               |
| DELETE | `/Booking/Cancel/{bookingId}`    | Cancel a booking                 |
| GET    | `/Booking/MyBookings`           | Mentee’s own bookings            |
| GET    | `/Booking/MentorBookings`       | Mentor’s own bookings            |
| GET    | `/Booking/AllBookings`          | All bookings (admin)             |

---

## 🧠 Key Concepts Covered

✔ Spring Boot REST API  
✔ JWT authentication & validation  
✔ Spring Security role-based access control  
✔ Stateless API design (no session)  
✔ BCrypt password hashing  
✔ Repository + Service architecture  
✔ Global exception handling  
✔ Input validation and DTOs  
✔ Business rule enforcement

---

## 🧪 Testing Instructions

1. Use **Postman** or similar API client  
2. Register user: **POST** /User/Register
3. Login user to receive JWT: **POST** /User/Login
4. Set JWT in Authorization header: Authorization: Bearer *<token>*
5. Make protected requests to secured endpoints

---

## ⚙️ Configuration

**`src/main/resources/application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/skillshare
spring.datasource.username=<your_mysql_username>
spring.datasource.password=<your_mysql_password>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=REPLACE_THIS_WITH_A_STRONG_SECRET
jwt.expiration-ms=3600000
jwt.issuer=skills-share-scheduler
```
## 💡 How to Run

*Create MySQL database:*
```properties
CREATE DATABASE skillshare;
```

Update database credentials in application.properties

*Build and run:*
```properties
mvn clean install
mvn spring-boot:run
```

## 📌 What You’ll Learn

✔ Secure REST API development  
✔ JWT authentication & token management  
✔ Role based access control  
✔ Exception handling patterns  
✔ Business layer design  
✔ Integration with MySQL using JPA  

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or pull requests to improve features, add enhancements, or fix bugs.

## 📝 License

This project is open-source and available for educational and professional use.
---

If you want **graphics, screenshots, Mermaid diagrams, or API example blocks** embedded into this README, just let me know and I can generate them!
::contentReference[oaicite:0]{index=0}

