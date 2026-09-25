# 🦷 Dental Clinic Appointment Management System — Backend

A RESTful backend for a dental clinic appointment management system, built with Java and Spring Boot.

The backend manages doctors, patients, dental services, and appointments, while providing authentication, authorization, and appointment management functionality.

## 📌 Overview

This backend provides the REST APIs and business logic required to manage the clinic's appointment workflow.

The application connects the React frontend with a PostgreSQL database using Spring Data JPA and Hibernate.

The main workflow is:

Patient
↓
Select Dental Service
↓
Enter Patient Information
↓
Select Date & Time
↓
Create Appointment
↓
Backend API
↓
PostgreSQL
↓
Doctor Dashboard
↓
Update Appointment Status

## ✨ Features

- 🔐 Doctor authentication using Spring Security and JWT
- 👨‍⚕️ Doctor management
- 👤 Patient management
- 🦷 Dental service management
- 🔗 Doctor–Service relationships
- 📅 Appointment creation and management
- 👨‍⚕️ Doctor-specific appointments
- 🔄 Appointment status management
- 🗄️ PostgreSQL database integration
- 🌐 RESTful API architecture

## 🛠️ Technologies

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok

## Main Entities

The application is built around the following main entities:

- Doctor
- Patient
- Service
- Appointment
- AppointmentStatus

Doctors can provide multiple dental services through a relationship between doctors and services.

Appointments are associated with the relevant patient, doctor, service, date, time, and appointment status.

## 🔐 Authentication

Doctor authentication is implemented using Spring Security and JWT.

The authentication flow is:

Doctor Login
↓
Email + Password
↓
Authentication
↓
JWT Token
↓
Authorization Header
↓
Protected API Endpoints

The authenticated doctor can access their own appointments through the authenticated user context.

## 📅 Appointment API

### Create Appointment

```http
POST /api/appointments
Creates a new appointment.

Update Appointment Status
PATCH /api/appointments/{appointmentId}/status
Updates the status of an appointment.

The authenticated doctor’s identity is obtained from the authentication context.

Example request:
{
  "status": "CONFIRMED"
}
Get Appointments by Doctor
GET /api/appointments/doctor/{doctorId}
Returns appointments associated with a specific doctor.

Get Current Doctor’s Appointments
GET /api/appointments/my-appointments
Returns appointments belonging to the currently authenticated doctor.

The doctor is identified through the authenticated user rather than requiring the frontend to provide the doctor ID.

🔄 Appointment Status
Appointments can have different statuses depending on the clinic workflow.
Example:
PENDING
   ↓
CONFIRMED
   ↓
COMPLETED

Appointments can also be marked as:CANCELLED

The appointment status is represented using a Java enum.

🗄️ Database
The application uses PostgreSQL as its relational database.

JPA/Hibernate is used to map Java entities to database tables and manage relationships between doctors, patients, services, and appointments.

Database Relationships
Doctor
   │
   ├── Doctor_Service ── Service
   │
   └── Appointment
          │
          ├── Patient
          └── Service
ERD
The database structure and relationships are illustrated below

Project Structure
src/
└── main/
    └── java/
        └── com.example.backend/
            ├── config/
            ├── controller/
            ├── dto/
            ├── entity/
            ├── repository/
            ├── security/
            └── service/
Controller
Handles HTTP requests and API responses.

Service
Contains the application’s business logic.

Repository
Handles database operations using Spring Data JPA.

Entity
Represents the database entities and their relationships.

DTO
Defines the data exchanged between the frontend and backend.

Security
Handles authentication and JWT-based authorization.

🔗 Frontend
The frontend application was developed separately using React.
Frontend repository:
https://github.com/mahaal-Nefaie/dental-clinic-frontend

👩‍💻 Author
Maha Al-Nefaie
