Career-Connect (Job Portal)
Career-Connect is a comprehensive backend for a job portal application, built using Spring Boot, Spring Security, and Spring Data JPA. It provides a complete API for students to find and apply for jobs, and for administrators to manage job postings, applications, and user accounts.

Features
General Features
Authentication: Secure, stateless JWT (JSON Web Token) authentication for all protected endpoints.

Role-Based Access Control (RBAC): Clear separation of permissions between two roles:

ROLE_STUDENT

ROLE_ADMIN

Data Validation: Server-side validation for operations.

Student Features (ROLE_STUDENT)
Register & Login: Students can create a new account and log in.

View & Filter Jobs: Access a public list of all available jobs with filtering by title, location, and company name.

Apply for Job: Students can apply for any open job. The system prevents duplicate applications.

View Application History: Students can see a list of all jobs they have applied for and the status of each application.

Resume Upload: Students can upload and manage their resume, which is linked to their profile.

Admin Features (ROLE_ADMIN)
Job Management (CRUD): Admins can create, read, update, and delete job postings.

Application Management:

View all applications submitted for a specific job.

Update the status of any application (e.g., "APPLIED", "REVIEWED", "REJECTED").

User Management:

View a list of all registered students.

Delete student accounts (which also cascade-deletes their associated applications).

Technology Stack
Java: Version 17

Spring Boot: Core application framework

Spring Security: For authentication (JWT) and authorization (RBAC)

Spring Data JPA: For database interaction and repository logic

Hibernate: JPA implementation

MySQL: Relational database

Maven: Dependency management and build tool

Lombok: To reduce boilerplate code (getters, setters, constructors)

jjwt: Java library for creating and parsing JSON Web Tokens

Prerequisites
Before you begin, ensure you have the following installed on your system:

JDK 17 or higher

Maven 3.6+

MySQL Server

Setup and Installation
Clone the Repository

Bash

git clone <your-repository-url>
cd career-connect
Create the MySQL Database Log in to your MySQL server and create the database.

SQL

CREATE DATABASE job_portal_db;
Configure the Application Open the src/main/resources/application.properties file and update the spring.datasource properties to match your MySQL setup.

Do not use the provided credentials in production.

Properties

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/job_portal_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update

# JWT Secret Key
jwt.secret.key=wA7aJ2o8o9sY1xP3bH5gE6vR4uC3iB0fK9lM8nQ7pV2zX1sW4eD5jG6kH8aF7tY9
File Upload Directory The application stores resume uploads in an uploads directory at the project's root level. This directory will be created automatically on startup if it doesn't exist.

Running the Application
You can run the application using the Maven wrapper:

Bash

# On macOS/Linux
./mvnw spring-boot:run

# On Windows
./mvnw.cmd spring-boot:run
The application will start, and the server will be accessible at http://localhost:8080.

Default Admin User
On the first startup, the application will automatically create a default admin user if one does not already exist:

Email: admin@portal.com

Password: admin123

You can use this account to log in at POST /api/auth/login and receive a JWT for accessing admin-protected endpoints.

API Endpoints
All endpoints are prefixed with /api.

Authentication Endpoints (/api/auth)
POST /api/auth/register/student

Description: Registers a new student account.

Body: User object (name, email, password).

Access: Public

POST /api/auth/login

Description: Authenticates a user (student or admin) and returns a JWT.

Body: LoginRequest (email, password).

Access: Public

Returns: LoginResponse (containing the JWT).

Public Job Endpoints (/api/jobs)
GET /api/jobs

Description: Get all available job postings.

Query Params (Optional):

title (String): Filter by job title (case-insensitive, partial match).

location (String): Filter by location (case-insensitive, partial match).

companyName (String): Filter by company name (case-insensitive, partial match).

Access: Public

Student Endpoints (/api/student)
(Requires ROLE_STUDENT JWT in Authorization header)

POST /api/student/jobs/{jobId}/apply

Description: Applies for the job with the specified jobId.

Access: Student

GET /api/student/applications

Description: Gets a list of all applications for the currently logged-in student.

Access: Student

POST /api/student/resume

Description: Uploads a resume for the student.

Body: multipart/form-data with a key "file".

Access: Student

Admin Endpoints (/api/admin)
(Requires ROLE_ADMIN JWT in Authorization header)

Job Management
POST /api/admin/jobs

Description: Creates a new job posting.

Body: Job object.

Access: Admin

PUT /api/admin/jobs/{jobId}

Description: Updates an existing job posting.

Body: Job object with updated details.

Access: Admin

DELETE /api/admin/jobs/{jobId}

Description: Deletes a job posting.

Access: Admin

Application Management
GET /api/admin/jobs/{jobId}/applications

Description: Gets all applications submitted for a specific jobId.

Access: Admin

PUT /api/admin/applications/{applicationId}/status

Description: Updates the status of a specific application.

Body: UpdateApplicationStatusDTO (e.g., {"status": "REVIEWED"}).

Access: Admin

User Management
GET /api/admin/users

Description: Gets a list of all users with the ROLE_STUDENT.

Access: Admin

DELETE /api/admin/users/{userId}

Description: Deletes a student account and all their associated applications.

Access: Admin
