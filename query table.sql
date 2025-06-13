-- 1. Database
CREATE DATABASE db_appmakanan;
USE db_appmakanan;

-- 2. Tabel User (mengandung Admin & Customer)
CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'customer') NOT NULL,
    ewalletBalance DOUBLE DEFAULT 1000000,
    rekeningBalance DOUBLE DEFAULT 1000000
);

-- 3. Tabel Restaurant
CREATE TABLE Restaurants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 4. Tabel MenuItem
CREATE TABLE MenuItems (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurants(id) ON DELETE SET NULL
);

-- 5. Tabel Cart
CREATE TABLE Carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT UNIQUE,
    FOREIGN KEY (customer_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- 6. Relasi Cart - MenuItem (Many-to-Many)
CREATE TABLE Cart_MenuItems (
    cart_id INT,
    menu_item_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (cart_id, menu_item_id),
    FOREIGN KEY (cart_id) REFERENCES Carts(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES MenuItems(id) ON DELETE CASCADE
);

-- 7. Tabel Orders
CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    totalAmount DOUBLE NOT NULL,
    payment_method ENUM('ewallet', 'debit') NOT NULL,
    payment_id INT,
    FOREIGN KEY (customer_id) REFERENCES Users(id) ON DELETE SET NULL
);

-- 8. Relasi Order - MenuItem (Many-to-Many)
CREATE TABLE Order_MenuItems (
    order_id INT,
    menu_item_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (order_id, menu_item_id),
    FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES MenuItems(id) ON DELETE CASCADE
);

-- 1. Tambahkan ke tabel Users
INSERT INTO Users (username, password, role)
VALUES ('admin123', 'adminpass', 'admin');

-- 2. Ambil ID user yang baru dimasukkan (gunakan LAST_INSERT_ID())
SET @new_user_id = LAST_INSERT_ID();

SELECT * FROM Users;

-- Insert into Restaurants
INSERT INTO Restaurants (name) VALUES
('Warung Makan Sederhana'),
('Resto Padang Maknyus'),
('Bakso & Mie Ayam Pak Budi'),
('Ayam Geprek Mantul'),
('Kopi Santai');

-- Insert into MenuItems
INSERT INTO MenuItems (name, price, restaurant_id) VALUES
('Nasi Goreng Spesial', 18000, 1),
('Mie Ayam Bakso', 15000, 3),
('Ayam Geprek Level 5', 20000, 4),
('Es Teh Manis', 5000, 3),
('Kopi Hitam', 7000, 5);