# Dairy Service Management System

A full-stack web application to manage daily milk requests, billing between customers and a dairy administrator.

This project represents a **real-world dairy workflow** with clean role separation, simple authentication, and manual billing logic used in many small dairy businesses.


## 🚀 Tech Stack

### Backend
- Java
- Spring Boot (REST APIs)
- Spring Data JPA
- MySQL
- Lombok

### Frontend
- React
- React Router
- Axios
- CSS

### Authentication
- Custom session-based login (kept simple for learning)


## 👥 User Roles

### 👤 Customer
- Register & Login
- Send daily milk requests
- View request status & history
- View bills (paid / unpaid)
- See total outstanding amount

### 🛠️ Admin
- Login (separate role)
- View all customer requests
- Accept / Reject / Complete requests
- Create bills manually
- Mark bills as Paid / Unpaid
- View dashboard reports



## 📌 Key Features

- Role-based access control
- Session-based authentication
- Daily milk request management
- Manual billing (realistic dairy use-case)
- Outstanding amount calculation
- REST API based architecture
- Clean and simple UI



## 🗂️ Project Modules

### Customer Module
- Register / Login
- New Milk Request
- Dashboard
- My Requests
- My Bills

### Admin Module
- Login
- Dashboard
- All Requests (manage status)
- Create Bill
- Bills List
- Customers List



## 🖼️ Screenshots

### 🔐 Login & Registration

| Register Page | Login Page |
|---------------|------------|
| <img src="Images/Reg.png" width="250"/> | <img src="Images/Login.png" width="250"/> |

### 👤 Customer Side

| Customer Dashboard | New Milk Request | My Requests | My Bills |
|-------------------|------------------|-------------|----------|
| <img src="Images/cCustomer Dashboard.png" width="240"/> | <img src="Images/cCreate Request.png" width="240"/> | <img src="Images/cAll requests.png" width="240"/> | <img src="Images/cAll Bills.png" width="240"/> |

### 🛠️ Admin Side

| Admin Dashboard | All Requests | All Customers | Create Bill | Bills List |
|-----------------|--------------|---------------|-------------|------------|
| <img src="Images/aAdmin Dashboard.png" width="210"/> | <img src="Images/aAll Requests.png" width="210"/> | <img src="Images/aAll Customers.png" width="210"/> | <img src="Images/aCreate Bill.png" width="210"/> | <img src="Images/aAll Bills.png" width="210"/> |

---
