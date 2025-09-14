Internal Payment Management System
Overview
The Internal Payment Management System is a role-based application that allows organizations to manage users, payments, and financial reports.
It supports three types of users:
Admin → Manage users and audit logs
Finance Manager → Manage payments and generate reports
Viewer → View reports only
The system ensures data integrity, traceability, and clean separation of concerns using DAO, DTO, and Service layers.

Technologies Used
Java 17+
JDBC for database connectivity
PostgreSQL/MySQL as the database
PlantUML for UML diagrams
Maven
GitHub for version control

📂 Project Structure
PaymentManagementSystem/
 ├── src/
 │   ├── tech/zeta/model/        # Entity classes (User, Payment, Category)
 │   ├── tech/zeta/dao/          # DAO Interfaces
 │   ├── tech/zeta/dao/impl/     # DAO Implementations
 │   ├── tech/zeta/service/      # Service classes (UserService, PaymentService)
 │   ├── tech/zeta/menu/         # Menu-driven UI (AdminMenu, FinanceManagerMenu, ViewerMenu)
 │   └── tech/zeta/util/         # DBUtil for database connection
 ├── resources/
 │   └── schema.sql              # SQL script to create tables (optional)
 |   |___ application.properties
 
🗄️ Database Schema
Users Table
userId (PK, BIGINT)
name (VARCHAR)
email (VARCHAR, unique)
password (VARCHAR)
role (VARCHAR) → lowercase
isActive (BOOLEAN)

Payments Table
paymentId (PK, BIGINT)
amount (DECIMAL)
type (VARCHAR, Title Case: Incoming/Outgoing)
categoryId (FK → Categories)
status (VARCHAR, Title Case: Pending/Completed)
managerId (FK → Users)
date (DATE)

Categories Table
categoryId (PK, INT)
categoryName (VARCHAR) → e.g., Salary, Vendor, Client Invoice

🚀 How to Run

Clone the repository: https://github.com/Lakshmi-mrudula-dolly/Internal_Payment_Management_System/

git clone 
cd PaymentManagementSystem


Set up the database:
Create a PostgreSQL/MySQL database (e.g., paymentdb).
Run the SQL script (resources/schema.sql) or manually create tables.
Update application.properties with your database credentials:

private static final String URL = "jdbc:postgresql://localhost:5432/paymentdb";
private static final String USER = "your-username";
private static final String PASSWORD = "your-password";


Compile and run the project:

javac -d bin src/**/*.java
java -cp bin tech.zeta.Main


Login with default admin:
Email: mrudula@gmail.com
Password: 12345678

User Roles & Access
Admin → Create, Update, Delete Users; View Audit Log
Finance Manager → Add Payment, Update Status, Generate Reports
Viewer → View Reports only

Reports
Monthly Report → Payments summary by category for the given month
Quarterly Report → Payments summary by category for the given quarter
