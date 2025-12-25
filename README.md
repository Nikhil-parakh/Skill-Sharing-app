📚 Skill-Share Scheduler — Backend Project (Spring Boot)
A role-based scheduling API that connects Mentors with Mentees, enabling secure user management, mentor slot availability, and booking workflows — built with Spring Boot, Spring Security, JWT, and MySQL.
________________________________________
🛠️ Tech Stack
•	Backend: Java, Spring Boot
•	Security: Spring Security, JWT (JSON Web Tokens)
•	Database: MySQL
•	ORM: Spring Data JPA
•	Password Encryption: BCrypt
•	Build: Maven
•	Error Handling: Global exception handler
________________________________________
🚀 Features
✔ User registration & login
✔ Stateless JWT authentication
✔ Role-based access: ADMIN, MENTOR, MENTEE
✔ Mentor: create, list, delete time slots
✔ Mentee: view available slots, book & cancel bookings
✔ Admin: view all users, delete users
✔ Centralized validation & error responses
________________________________________
🔐 Authentication Flow
1.	User registers → password encrypted with BCrypt
2.	User logs in → returns a JWT token
3.	Client sends JWT in Authorization: Bearer <token>
4.	Spring Security + custom JWT filter verifies token
5.	Requests proceed based on roles & permissions
________________________________________
Security Configuration
•	Disabled CSRF (API only)
•	Stateless session (SessionCreationPolicy.STATELESS)
•	JWT filter added before username/password filter
•	Custom UserDetailsService & AuthenticationProvide 
•	Secure endpoints using role checks
________________________________________
📦 Project Structure
src
|── main
│  ├ java
│   │   └── com.example.Skills_Share_Scheduler
│   │       ├── Config          (Security + JWT setup)
│   │       ├── Controller      (API endpoints)
│   │       ├── Entity          (JPA models)
│   │       ├── Exceptions      (Custom exceptions & handler)
│   │       ├── Repository      (Spring Data repos)
│   │        └── Service        (Business logic)
│   └── resources
│       ├── application.properties
│       └── db migration/sql
├── test
└── pom.xml
________________________________________
🧠 Core Concepts Covered
✔ JWT Authentication
✔ Role-based Authorization
✔ Secure Endpoints (authenticated(), hasRole())
✔ Global Exception Handling
✔ Input Validation
✔ Stateless API Design
✔ Business Logic Enforcement
________________________________________
📋 API Endpoints
🔓 Public
Method	Endpoint	Description
POST	/User/Register	Register new user
POST	/User/Login	Login & get JWT
________________________________________
👤 User Management
Method	Endpoint	Access
GET	/User/User_Id/{id}	Authenticated
GET	/User/Username/{username}	Authenticated
PUT	/User/Update/{id}	Authenticated
DELETE	/User/Delete/{id}	Authenticated (ADMIN or self)
GET	/User/All_Users	ADMIN
________________________________________
⏲️ Slots
Method	Endpoint	Description
POST	/Slot/Create	Mentor creates slot
GET	/Slot/MySlots	Get mentor’s own slots
GET	/Slot/OfMentor/{id}	Get slots of any mentor
DELETE	/Slot/Delete/{slotId}	Delete slot
________________________________________
📅Booking
Method	Endpoint	Description
POST	/Booking/Create/{slotId}	Mentee books slot
DELETE	/Booking/Cancel/{bookingId}	Cancel booking
GET	/Booking/MyBookings	Mentee’s bookings
GET	/Booking/MentorBookings	Mentor’s bookings
GET	/Booking/AllBookings	All bookings
________________________________________
🧩 Security Configuration
•	Disabled CSRF (API only)
•	Stateless session (SessionCreationPolicy.STATELESS)
•	JWT filter added before username/password filter
•	Custom UserDetailsService & AuthenticationProvider
•	Secure endpoints using role checks
________________________________________
📥 Global Exception Handling
Handled via @RestControllerAdvice:
✔ User already exists
✔ Illegal arguments
✔ Validation errors
✔ Runtime exceptions
✔ Unknown server errors
Each returns structured JSON:
{
  "timestamp": "2025-xx-xxTxx:xx:xx",
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed message"
}
________________________________________
✅ Best Practices Followed
✔ Layered architecture (Controller → Service → Repo)
✔ DTOs for input/output
✔ Centralized error handling
✔ Secure password storage
✔ Stateless token handling
✔ Clean permission checks
________________________________________
🛠 Setup
1.	Create MySQL DB: skillshare
2.	Update application.properties
3.	Build & run:
4.	mvn clean install
5.	mvn spring-boot:run
6.	Test via your API client
________________________________________

⭐ Outcomes
•	A secure role-based scheduler backend
•	Full authentication & authorization
•	Robust business logic for slots & bookings
•	Structured error handling
________________________________________
📌 Learnings
Spring Boot REST • Security • JWT • MySQL • JPA • Validation • Exception Handling

