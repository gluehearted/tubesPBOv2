-- 1. Database
CREATE DATABASE db_appmakanan;
USE db_appmakanan;

-- 2. Tabel User (mengandung Admin & Customer)
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'customer') NOT NULL,
    ewalletBalance DOUBLE DEFAULT 0.0,
    rekeningBalance DOUBLE DEFAULT 0.0
);
ALTER TABLE user MODIFY password VARCHAR(255) NOT NULL;
select * from user;

-- 3. Tabel Restaurant
CREATE TABLE Restaurant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 4. Tabel MenuItem
CREATE TABLE MenuItem (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES Restaurant(id) ON DELETE SET NULL
);

-- 5. Tabel Cart
CREATE TABLE Cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT UNIQUE,
    FOREIGN KEY (customer_id) REFERENCES User(id) ON DELETE CASCADE
);

-- 6. Relasi Cart - MenuItem (Many-to-Many)
CREATE TABLE Cart_MenuItem (
    cart_id INT,
    menu_item_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (cart_id, menu_item_id),
    FOREIGN KEY (cart_id) REFERENCES Cart(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES MenuItem(id) ON DELETE CASCADE
);

-- 7. Tabel Order
CREATE TABLE `Order` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    totalAmount DOUBLE NOT NULL,
    payment_method ENUM('ewallet', 'debit') NOT NULL,
    payment_id INT,
    FOREIGN KEY (customer_id) REFERENCES User(id) ON DELETE SET NULL
);

-- 8. Relasi Order - MenuItem (Many-to-Many)
CREATE TABLE Order_MenuItem (
    order_id INT,
    menu_item_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (order_id, menu_item_id),
    FOREIGN KEY (order_id) REFERENCES `Order`(id) ON DELETE CASCADE,
    FOREIGN KEY (menu_item_id) REFERENCES MenuItem(id) ON DELETE CASCADE
);

-- 9. Tabel EWalletTopUp
CREATE TABLE EWalletTopUp (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    topup_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'success', 'failed') DEFAULT 'pending',
    description VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- 1. Tambahkan ke tabel User
INSERT INTO User (username, password)
VALUES ('admin123', 'adminpass');

-- 2. Ambil ID user yang baru dimasukkan (gunakan LAST_INSERT_ID())
SET @new_user_id = LAST_INSERT_ID();

-- 3. Tambahkan ke tabel Admin
INSERT INTO Admin (id)
VALUES (@new_user_id);

select * from user;

INSERT INTO Restaurant (name) VALUES
('Warung Makan Sederhana'),
('Resto Padang Maknyus'),
('Bakso & Mie Ayam Pak Budi'),
('Ayam Geprek Mantul'),
('Kopi Santai');

INSERT INTO MenuItem (name, price, restaurant_id) VALUES
('Nasi Goreng Spesial', 18000, 1),
('Mie Ayam Bakso', 15000, 3),
('Ayam Geprek Level 5', 20000, 4),
('Es Teh Manis', 5000, 3),
('Kopi Hitam', 7000, 5);