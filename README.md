# Hotel Management System

A desktop-based **Hotel Management System** developed using **Java** by applying proper
**Object Oriented Programming (OOP)** principles.  
This project uses **JavaFX** for the graphical user interface and **MySQL** for database management.
Version control and documentation are maintained using **GitHub**.

This project was developed as part of the **CSE 110 – Object Oriented Programming** course.

---

##  Project Overview

The Hotel Management System is designed to manage the daily operations of a small hotel.
It allows hotel staff to handle guest check-in, checkout, room management, and guest record
maintenance efficiently through a simple graphical interface.

The main objective of this project is to demonstrate the practical application of
Object Oriented Programming concepts such as **encapsulation, inheritance, polymorphism,
and abstraction** in a real-world system.

---

## Key Features

- Guest check-in with **full payment only**
- Checkout using **mobile number** (unique identifier)
- Validation of guest information:
    - NID must be **17 digits**
    - Mobile number must be **11 digits**
- Management of **30 hotel rooms** with different room types
- View all guest details in a table format
- Update room availability
- Backup guest records to a **CSV file**
- Clean and user-friendly JavaFX interface

---

## Object Oriented Programming Concepts Used

### Encapsulation
All model classes use **private fields** and expose data through
**public getter and setter methods**, ensuring controlled access to data.

### Inheritance
The `Guest` class extends the `Person` class.
Common properties such as name and mobile number are reused through inheritance,
reducing code duplication.

### Polymorphism
The `HotelService` interface is implemented by `HotelServiceImpl`.
This allows the system to use service methods through an interface reference,
making the application flexible and extensible.

### Abstraction
The user interface communicates only with the **service layer**.
Database-related operations are hidden inside the repository layer,
which ensures separation of concerns.

---

## 🗄️ Database Design

The system uses **MySQL** as the database.

### Tables Used:
- `login` – stores login credentials
- `room` – stores room number, type, availability, and price
- `guest` – stores guest information such as name, NID, mobile, address, room, and payment

**For security reasons, database credentials (passwords) are not included in this repository.**
Each user must configure their own local database credentials.

---

## Technologies Used

- **Java (JDK 25)**
- **JavaFX** (GUI)
- **MySQL** (Database)
- **Git & GitHub** (Version Control)
- **IntelliJ IDEA** (IDE)

---

## How to Run the Project

1. Clone or download the repository
2. Open the project in **IntelliJ IDEA**
3. Set **Project SDK** to **JDK 25**
4. Configure **JavaFX SDK** in VM options
5. Create the database using the provided SQL schema
6. Update database credentials in `DatabaseConnection.java`
7. Run `Main.java`

---

## Group Members

This project was developed as a **group project** by the following members:

| Name                 | ID            | 
|----------------------|---------------|
| Tawfique Abrar Karim | 2025-1-60-128 |
| Khatuna Jannat Ikra  |2025-1-60-132  |
| Bushra Islam Sinthy  | 2025-1-60-395 |
| Mohammad Hasan Mahmud |2025-1-60-126 | 

---

## Course Information
- **Course:** CSE 110 – Object Oriented Programming
- **Semester:** Fall 2025
- **University:** *East West University*  

## License

This project is licensed under the **MIT License** by Tawfique Abrar Karim.
