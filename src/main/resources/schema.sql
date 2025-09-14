-- Schema for Internal Payment Management System

-- Drop existing tables if they exist
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

-- ==========================
-- USERS TABLE
-- ==========================
CREATE TABLE users (
    userId BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    isActive BOOLEAN DEFAULT TRUE
);

-- Insert default Admin
INSERT INTO users (name, email, password, role, isActive)
VALUES ('Mrudula', 'mrudula@gmail.com', '12345678', 'admin', TRUE);

-- ==========================
-- CATEGORIES TABLE
-- ==========================
CREATE TABLE categories (
    categoryId SERIAL PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL
);

-- Insert default categories
INSERT INTO categories (categoryName) VALUES ('Salary');
INSERT INTO categories (categoryName) VALUES ('Vendor');
INSERT INTO categories (categoryName) VALUES ('Client Invoice');

-- ==========================
-- PAYMENTS TABLE
-- ==========================
CREATE TABLE payments (
    paymentId BIGSERIAL PRIMARY KEY,
    amount DECIMAL(12,2) NOT NULL,
    type VARCHAR(20) CHECK (type IN ('Incoming', 'Outgoing')),
    categoryId INT NOT NULL REFERENCES categories(categoryId),
    status VARCHAR(20) CHECK (status IN ('Pending', 'Completed')),
    managerId BIGINT NOT NULL REFERENCES users(userId),
    date DATE NOT NULL
);
