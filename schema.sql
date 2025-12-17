-- Create database
CREATE DATABASE IF NOT EXISTS hotel_management_system;
USE hotel_management_system;


-- LOGIN TABLE
CREATE TABLE login (
                       username VARCHAR(50) PRIMARY KEY,
                       password VARCHAR(100) NOT NULL
);

-- Default login user
INSERT INTO login (username, password)
VALUES ('admin', 'admin123');


-- ROOM TABLE

CREATE TABLE room (
                      room_number INT PRIMARY KEY,
                      room_type VARCHAR(20),
                      availability VARCHAR(20),
                      price INT
);


INSERT INTO room VALUES
                     (101,'Standard','Available',3000),
                     (102,'Standard','Available',3000),
                     (103,'Standard','Available',3000),
                     (104,'Standard','Available',3000),
                     (105,'Standard','Available',3000),
                     (106,'Standard','Available',3000),
                     (107,'Standard','Available',3000),
                     (108,'Standard','Available',3000),
                     (109,'Standard','Available',3000),
                     (110,'Standard','Available',3000);


INSERT INTO room VALUES
                     (201,'Deluxe','Available',4500),
                     (202,'Deluxe','Available',4500),
                     (203,'Deluxe','Available',4500),
                     (204,'Deluxe','Available',4500),
                     (205,'Deluxe','Available',4500),
                     (206,'Deluxe','Available',4500),
                     (207,'Deluxe','Available',4500),
                     (208,'Deluxe','Available',4500),
                     (209,'Deluxe','Available',4500),
                     (210,'Deluxe','Available',4500);


INSERT INTO room VALUES
                     (301,'Suite','Available',7000),
                     (302,'Suite','Available',7000),
                     (303,'Suite','Available',7000),
                     (304,'Suite','Available',7000),
                     (305,'Suite','Available',7000),
                     (306,'Suite','Available',7000),
                     (307,'Suite','Available',7000),
                     (308,'Suite','Available',7000),
                     (309,'Suite','Available',7000),
                     (310,'Suite','Available',7000);


-- GUEST TABLE

CREATE TABLE guest (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100),
                       nid VARCHAR(17),
                       mobile VARCHAR(11),
                       address VARCHAR(200),
                       room INT,
                       paid INT,
                       checkin_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (room) REFERENCES room(room_number)
);