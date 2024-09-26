CREATE DATABASE PROJECT;
USE PROJECT;

-- Customers table ------------------------

CREATE TABLE IF NOT EXISTS customers(
customer_id INT PRIMARY KEY,
customer_name VARCHAR(40) NOT NULL,
email VARCHAR(20),
address VARCHAR(30),
phone_number INT
);

ALTER TABLE customers MODIFY phone_number BIGINT(20);

INSERT INTO customers
VALUES
(101,"John","john@gmail.com","2-23A flat Raidurg ",8908678932),
(102,"Peter","peter@gmail.com","12/3 building Hastinapur",9908678932),
(103,"Jack","jack@gmail.com","opposite fedral bank King koti",7908678932),
(104,"Amy","Amy@gmail.com","Jyothi reddy hostel LB Nagar",8998678932),
(105,"Mary","Mary@gmail.com","Besides SBI bank Victoria",8907778932);

SELECT * FROM customers;


-- product TABLE---------------------------------

CREATE TABLE IF NOT EXISTS products(
product_id INT PRIMARY KEY,
product_name VARCHAR(40) ,
price INT,
quantity_in_stock INT,
category_id INT,
FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

SELECT * FROM products;

 -- ALTER TABLE products MODIFY COLUMN product_id VARCHAR(20);
-- product_id  :: ASIN 

INSERT INTO products
VALUES
(120111,"Ear buds ",999,9,1),
(120112,"TV ",29899,5,1),
(120113,"Micro wave ",1999,2,2),
(120114,"spoon set for dinner table",389,9,1),
(120115,"Kurta with pant",489,13,3);




-- Category table details-------------------------------

CREATE TABLE IF NOT EXISTS categories(
    category_id INT PRIMARY KEY,
    category_name VARCHAR(30)
);

INSERT INTO categories
VALUES
(1, "Electronics"),
(2,"Home appliances"),
(3,"Clothing");

SELECT * FROM categories;


-- ORDERS TABLE--------------------------------------------

CREATE TABLE IF NOT EXISTS orders(
order_id INT PRIMARY KEY,
customer_id INT,
order_date DATE,
total_amount DECIMAL,
FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO orders VALUES
(11103, 101, '2023-10-04', 2500),
(11104, 102, '2023-11-04', 1500),
(11105, 103, '2024-05-04', 900),
(11106, 102, '2024-09-04', 1200);

DELETE FROM orders WHERE order_id IN (11103, 11106);


SELECT * FROM orders;

-- order details table----------------------------------

CREATE TABLE IF NOT EXISTS order_details(
order_details_id INT PRIMARY KEY,
order_id INT,
product_id INT,
quantity INT,
price INT,
FOREIGN KEY (order_id ) REFERENCES orders(order_id ),
FOREIGN KEY (product_id ) REFERENCES products(product_id )
);


INSERT INTO order_details VALUES 
(1, 11103, 120111, 9, 999), 
(2, 11105, 120113, 2, 1999), 
(3, 11106, 120115, 13, 489);

SELECT * FROM order_details;


-- PAYMENTS TABLE

CREATE TABLE IF NOT EXISTS payments(
payment_id INT PRIMARY KEY,
order_id INT,
payment_date DATE,
payment_mode VARCHAR(20),
amount_paid DECIMAL,
FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

INSERT INTO payments
VALUES
(9989,11103,'2023-10-14',"Cash",999),
(9979,11105,'2024-05-04',"online",1999);

SELECT * FROM payments;


-- INVENTORY TABLE------------------------------------------------
-- : it keeps the tracks the stock levels of products.

CREATE TABLE IF NOT EXISTS inventory(
product_id INT,
quantity_available INT,
restock_date DATE,
FOREIGN KEY (product_id ) REFERENCES products(product_id )
);

INSERT INTO inventory 
VALUES
(120111,9,'2023-10-10'),
(120113,2,'2024-09-20');

SELECT * FROM inventory;