Career-Connect 🚀

Career-Connect is a robust backend for a job portal application built with Spring Boot, Spring Security, and Spring Data JPA. It provides a secure, role-based REST API for students to find and apply for jobs, and for administrators to manage jobs, applications, and users.

✨ Features
👨‍💼 General
Authentication: Secure, stateless JWT (JSON Web Token) authentication.

Role-Based Access Control (RBAC): Clear distinction between ROLE_STUDENT and ROLE_ADMIN.

Database Seeding: Automatically creates a default admin user on first startup.

🎓 Student (ROLE_STUDENT)
Register & Login: Students can create an account and log in.

View & Filter Jobs: View a list of all available jobs, with filtering by title, location, and company name.

Apply for Job: Apply for a job. The system prevents duplicate applications.

View Applications: Get a list of all applications they have submitted.

Resume Upload: Upload a resume (PDF, etc.) which is stored and linked to their user ID.

⚙️ Admin (ROLE_ADMIN)
Job Management (CRUD): Create, Update, and Delete job postings.

Application Management:

View all applications submitted for a specific job.

Update the status of any application (e.g., "APPLIED", "REVIEWED", "REJECTED").

User Management:

View a list of all registered students.

Delete a student account (which also cleans up their associated applications).

🛠 Technology Stack
Framework: Spring Boot 3.5.6

Language: Java 17

Security: Spring Security, JWT (jjwt)

Database: Spring Data JPA (Hibernate), MySQL

API: Spring Web (RESTful)

Build: Maven

Utilities: Lombok

📋 Prerequisites
JDK 17 or higher

Maven 3.6+

MySQL Server
