USE RENOMAXACCOUNTINGDB;

DROP TABLE IF EXISTS SUPPLIERS;
DROP TABLE IF EXISTS MAINDESCRIPTION;
DROP TABLE IF EXISTS SUBDESCRIPTION;
DROP TABLE IF EXISTS EXPENSES;
DROP TABLE IF EXISTS CLIENTS;

CREATE TABLE SUPPLIERS(
    SUPPLIERID INT AUTO_INCREMENT PRIMARY KEY,
    SUPPLIERNAME VARCHAR(64) NOT NULL
);

CREATE TABLE MAINDESCRIPTION(
    MAINDESCRIPTIONID INT AUTO_INCREMENT PRIMARY KEY,
    MAINDESCRIPTIONNAME VARCHAR(128)
);

CREATE TABLE SUBDESCRIPTION(
    SUBDESCRIPTIONID INT AUTO_INCREMENT PRIMARY KEY,
    SUBDESCRIPTIONNAME VARCHAR(128)
);

CREATE TABLE EXPENSES(
    EXPENSEID INT AUTO_INCREMENT PRIMARY KEY,
    DATETIME DATE NOT NULL,
    SUPPLIERID INT NOT NULL,
    MAINDESCRIPTIONID INT NOT NULL,
    SUBDESCRIPTIONID INT,
    SUBTOTAL DECIMAL(6,2) NOT NULL,
    GST DECIMAL(6,3) NOT NULL,
    QST DECIMAL(6,3) NOT NULL,
    TOTAL DECIMAL(6,2) NOT NULL,
    CONSTRAINT FOREIGN KEY ('SUPPLIERID') REFERENCES 'SUPPLIERS' ('SUPPLIERID') ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY ('MAINDESCRIPTIONID') REFERENCES 'MAINDESCRIPTION' ('MAINDESCRIPTIONID') ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY ('SUBDESCRIPTIONID') REFERENCES 'SUBDESCRIPTION' ('SUBDESCRIPTIONID') ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CLIENTS(
    CLIENTID INT AUTO_INCREMENT PRIMARY KEY,
    CLIENTNAME VARCHAR(64) NOT NULL,
    STREET VARCHAR(64) NOT NULL,
    CITY VARCHAR(64) NOT NULL,
    PROVINCE VARCHAR(64) NOT NULL,
    POSTALCODE VARCHAR(64) NOT NULL,
    HOMEPHONE VARCHAR(64) NOT NULL,
    CELLPHONE VARCHAR(64) NOT NULL,
    EMAIL VARCHAR(64) NOT NULL,
);