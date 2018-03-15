USE RENOMAXACCOUNTINGDB;

DROP TABLE IF EXISTS EXPENSES;
DROP TABLE IF EXISTS INVOICES;
DROP TABLE IF EXISTS INVOICEDESCRIPTIONS;
DROP TABLE IF EXISTS CLIENTS;
DROP TABLE IF EXISTS SUPPLIERS;
DROP TABLE IF EXISTS MAINDESCRIPTION;
DROP TABLE IF EXISTS SUBDESCRIPTION;

CREATE TABLE SUPPLIERS(
    SUPPLIERID INT AUTO_INCREMENT PRIMARY KEY,
    SUPPLIERNAME VARCHAR(64) UNIQUE NOT NULL
);

CREATE TABLE MAINDESCRIPTION(
    MAINDESCRIPTIONID INT AUTO_INCREMENT PRIMARY KEY,
    MAINDESCRIPTIONNAME VARCHAR(128) UNIQUE NOT NULL    
);

CREATE TABLE SUBDESCRIPTION(
    SUBDESCRIPTIONID INT AUTO_INCREMENT PRIMARY KEY,
    SUBDESCRIPTIONNAME VARCHAR(128) UNIQUE NOT NULL
);

CREATE TABLE EXPENSES(
    EXPENSEID INT AUTO_INCREMENT PRIMARY KEY,
    EXPENSENUMBER INT NOT NULL,
    DATETIME DATE NOT NULL,
    SUPPLIER VARCHAR(64) NOT NULL,
    MAINDESCRIPTION VARCHAR(64) NOT NULL,
    SUBDESCRIPTION VARCHAR(64),
    SUBTOTAL DECIMAL(6,2) NOT NULL,
    GST DECIMAL(6,3),
    QST DECIMAL(6,3),
    TOTAL DECIMAL(6,2) NOT NULL
);

CREATE TABLE CLIENTS(
    CLIENTID INT AUTO_INCREMENT PRIMARY KEY,
    CLIENTNAME VARCHAR(64) UNIQUE NOT NULL DEFAULT '',
    STREET VARCHAR(64) NOT NULL DEFAULT '',
    CITY VARCHAR(64) NOT NULL DEFAULT '',
    PROVINCE VARCHAR(64) NOT NULL DEFAULT '',
    POSTALCODE VARCHAR(7) NOT NULL DEFAULT '',
    HOMEPHONE VARCHAR(64) NOT NULL DEFAULT '',
    CELLPHONE VARCHAR(64) NOT NULL DEFAULT '',
    EMAIL VARCHAR(64) NOT NULL DEFAULT ''
);

CREATE TABLE INVOICES(
    INVOICEID INT AUTO_INCREMENT PRIMARY KEY,
    INVOICENUMBER INT NOT NULL,
    INVOICEDATE DATE NOT NULL,
    CLIENT VARCHAR(64) NOT NULL,
    SUBTOTAL DECIMAL(6,2) NOT NULL,
    GST DECIMAL(6,3),
    QST DECIMAL(6,3),
    TOTAL DECIMAL(6,2) NOT NULL,
    INVOICESENT BOOLEAN
);

CREATE TABLE INVOICEDESCRIPTIONS(
    INVOICEDESCRIPTIONID INT AUTO_INCREMENT PRIMARY KEY,
    INVOICENUMBER INT NOT NULL,
    INVOICEDESCRIPTION VARCHAR(128) NOT NULL,
    PRICE DECIMAL(6,2) NOT NULL
);

INSERT INTO SUPPLIERS(SUPPLIERID, SUPPLIERNAME) VALUES
    (1, "Adaptel"),
    (2, "Addisson"),
    (3, "ANDRE VIGER"),
    (4, "Bell Canada"),
    (5, "BMR"),
    (6, "Bureau en Gros"),
    (7, "Canada Trust"),
    (8, "Ceramic Royal"),
    (9, "Christian Automobile"),
    (10, "Corporate Express"),
    (11, "Costco"),
    (12, "Docteur du Pare-Brise"),
    (13, "Enreprise Loc. D'auto"),
    (14, "Esso"),
    (15, "Fido"),    
    (16, "Future Shop"),
    (17, "Home Depot"),
    (18, "Ikea"),
    (19, "Lambert"),
    (20, "Makita"),
    (21, "Mc Donalds"),
    (22, "Mondial"),
    (23, "Nordex"),
    (24, "Patrick Morin"),
    (25, "Pave Luxe"),
    (26, "Pavigres"),
    (27, "Peinture Micca"),
    (28, "Petro-Canada"),
    (29, "Plomberie Lambert"),
    (30, "Probec"),
    (31, "Reno-Depot"),
    (32, "Rona l'entrepot"),
    (33, "Shell"),
    (34, "Simplex"),
    (35, "TD Canada Trust"),
    (36, "Ultramar"),
    (37, "Various"),
    (38, "Volkswagen"),
    (39, "Wolseley Canada Inc.");

INSERT INTO MAINDESCRIPTION(MAINDESCRIPTIONID, MAINDESCRIPTIONNAME) VALUES
    (1, "Auto Loan"),
    (2, "Auto Maintenance"),
    (3, "Ceramic"),
    (4, "Costco"),
    (5, "Gas"),
    (6, "Material"),
    (7, "Phone"),
    (8, "Restaurant"),
    (9, "Tools"),
    (10, "Various");

INSERT INTO SUBDESCRIPTION(SUBDESCRIPTIONID, SUBDESCRIPTIONNAME) VALUES
    (1, "Drill"),
    (2, "Electricity"),
    (3, "Paint"),
    (4, "Plumbing"),
    (5, "Saw");

INSERT INTO CLIENTS(CLIENTID, CLIENTNAME, STREET, CITY, PROVINCE, POSTALCODE, HOMEPHONE, CELLPHONE, EMAIL) VALUES
    (1, "9174 - 5281 Quebec inc ", "6360 Beaubien", "Montreal", "Quebec", "H1M-3G8", "", "", ""),
    (2, "Alessi Caterina", "7164 12 Av.", "Montreal", "Quebec", "H2A-2Y3", "514-721-7861", "", ""),
    (3, "Argento Alfonso", "6395 Val Marie", "Saint-Leonard", "Quebec", "H1P-3A3", "514-323-9220", "", ""),
    (4, "Assurance AXA", "", "", "", "", "", "", ""),
    (5, "Assurance des'Jardin", "", "", "", "", "", "", ""),
    (6, "Barroso Teresa", "7422 Boul. Roy-Rene'", "Anjou", "Quebec", "H1K-3G6", "", "514-655-7064", ""),
    (7, "Bellinfante Gino & Michelle", "6855 Pierre Gadois", "Montreal", "Quebec", "H1M-2X7", "514-354-4243", "", ""),    
    (8, "Bertrand Johnny", "15679 Rue Pierre-Legault", "Pierrefond", "Quebec", "H9H-4V9", "514-624-1734", "514-244-8063", ""),   
    (9, "Bielsky Tom", "22 Gabrielle-Roy", "Verdun", "Quebec", "H3E-1M3", "514-768-2954", "", ""),   
    (10, "Bruno Marcella", "12020 Longtin", "Montreal", "Quebec", "H4K-2N8", "514-335-2531", "", ""),
    (11, "Bucaro Salvatore", "9243 Louis-Dessaulles", "Montreal", "Quebec", "H1E-6S7", "514-494-1907", "", ""),
    (12, "Caruso Giovannina", "295 Rue Hachez", "Lasalle", "Quebec", "H8R-4A2", "514-364-9120", "", ""),
    (13, "Ciaralli Alfiere", "5010 Pierre Bernard", "Montreal", "Quebec", "H1K-2R7", "", "", ""),
    (14, "Clemente Adriele", "8520 Perras Apt. 2", "Montreal", "Quebec", "H1E-5C7", "", "", ""),
    (15, "D'Ambrosio Carmela", "8330 D'Aunis", "St. Leonard", "Quebec", "H1R-2N2", "514-327-1850", "", ""),
    (16, "Davino Carmela", "8964 Rue Meunier", "Montreal", "Quebec", "H2N-1W2", "514-388-3397", "514-771-3397", ""),
    (17, "Delia Antonio", "5705 Rue Brunetiere", "Saint-Leonard", "Quebec", "H1S-1B5", "514-256-4396", "", ""),
    (18, "Di Manno Dominic", "11875 Andre' Dumas", "Montreal", "Quebec", "H1E-3S4", "", "", ""),
    (19, "Diagne Bibata", "28 Rue Gabrielle-Roy", "Verdun", "Quebec", "H3E1M3", "514-769-7738", "", ""),
    (20, "Dr. Miller Richard", "476 ch. Grande-cote", "Rosemere", "Quebec", "J7A-1L8", "", "", ""),
    (21, "Dr. Steven Krychmen", "315 Bl. Brunswick", "Pointe-Claire", "Quebec", "H9R-5M7", "514-486-6383", "514-262-1171", "skrychmen@hotmail.com"),
    (22, "Furneri Giuseppe", "5750 Lavoisier", "Saint-Leonard", "Quebec", "H1P-1C3", "", "", ""),
    (23, "Gilletz Jordan", "174 Braeside", "D.D.O.", "Quebec", "H9A-2B3", "", "514-892-0002", ""),
    (24, "Group Lessard", "611 Av Meloche", "Dorval", "Quebec", "H9P-2T1", "", "514-821-3826", "jeanpierre.riverin@lessard.ws"),
    (25, "Herman Michael", "44 Westland Dr", "Montreal-Ovest", "Quebec", "H4X-1M2", "514-484-2076", "514-816-7116", ""),
    (26, "Iacobacci Mario", "3856 Av Draper", "Montreal", "Quebec", "H4A-2P1", "", "", ""),
    (27, "Intact Assurance", "2450 Rue Girouard", "St. hyacinthe", "Quebec", "", "", "", ""),
    (28, "Iorio Michel", "17 Rue Gabrielle-Roy", "Verdun", "Quebec", "H3E-1M3", "", "", ""),
    (29, "Isernia Vincenzo", "12381 Eudore-Dubeau", "Montreal", "Quebec", "H1E-5V2", "514-648-8112", "", ""),
    (30, "Jean Luc & Isolia", "7365 Av. Andre Ampere", "Montreal", "Quebec", "H1E-3L4", "", "", ""),
    (31, "Jones Bryan", "585 Mitchell", "Mont-Royal", "Quebec", "H3R-1L5", "514-341-5049", "", ""),
    (32, "Josie Greco Muro", "12260 Alfred Nobel", "Montreal", "Quebec", "H1E-4T5", "", "", ""),
    (33, "Kearney Robert", "3447 Northcliffe", "Montreal", "Quebec", "H4A-3K8", "", "", ""),
    (34, "Lapolla M. Pierfelice C.", "12366 Eudore-Dubeau", "Montreal", "Quebec", "H1E-5V3", "514-494-6197", "514-573-7763", ""),
    (35, "Lavallee Richard", "6682 Rue Jean-Tavernier", "Montreal", "Quebec", "H1M-2E6", "", "", ""),
    (36, "Lhuillier Gisele", "1411 Notre dame west #3", "Montreal", "Quebec", "H3C-4J6", "", "514-390-1346", ""),
    (37, "Maluorni Franco", "4353 Milan", "St. Leonard", "Quebec", "H1S-1H1", "514-376-3368", "514-865-3368", ""),
    (38, "Marussi Johnny", "10457 Av. Plaza", "Montreal-Nord", "Quebec", "H1H-4M1", "514-322-2197", "", ""),
    (39, "Miller Richard", "457 Kindersley", "Mount-Royal", "Quebec", "H3R-1S1", "", "", ""),
    (40, "Needleman Lisa & Wein T.", "462 Kenaston Avenue", "Mount Royal", "Quebec", "H3R-1M9", "514-341-0757", "", ""),
    (41, "Nicolazzo Giovannina", "295 Hachez", "Ville Lasalle", "Quebec", "H8R-4A2", "514-364-9120", "", ""),
    (42, "Panameno Margarita", "7880 Bl. Perras", "Montreal", "Quebec", "H1E-4X2", "", "", ""),
    (43, "Pascale Anna", "11834 Frigon", "Montreal", "Quebec", "H3M-2R7", "", "", ""),
    (44, "Perrone Flora Paterno A.", "12560 39 Av.", "Montreal", "Quebec", "H1E-2E2", "514-648-0186", "", ""),
    (45, "Perry Shak", "2176 Ch. Fulton", "Mont-Royal", "Quebec", "H3R-2L4", "", "", ""),
    (46, "Petrocco Marcello", "8369 Ernest-Ouimet", "Montreal", "Quebec", "H1E-7H2", "", "514-913-6653", ""),
    (47, "Piccolo Francesco", "7350 9 Av.", "Montreal", "Quebec", "H2A-3C1", "514-727-1772", "", ""),
    (48, "Renda Giuseppe", "12350 52 Av.", "Montreal", "Quebec", "H1E-6S5", "514-881-8579", "", ""),
    (49, "Renovations Ma Maison ML Inc.", "4635 de la Fabrique", "Laval", "Quebec", "H7C-1E1", "450-661-5115", "514-592-8632", ""),
    (50, "Riendeau Lise", "77 Rue Gignac", "Repentigny", "Quebec", "J5Y-1X4", "450-585-5013", "", ""),
    (51, "Riverin Jean-Pierre", "2825 rue des Harfangs", "Montreal", "Quebec", "H4R-2W1", "", "514-821-3826", ""),
    (52, "Roy Daniel", "26 Merineau", "Kirkland", "Quebec", "H9J-3V8", "", "", ""),
    (53, "Santilli Graziella & Enzo Anoja", "5949 Rue de Cadillac", "Montreal", "Quebec", "H1M-2L9", "", "", ""),
    (54, "Santilli Renato", "4695 Rue turpin", "Pierrefonds", "Quebec", "H9K-1K1", "514-624-9083", "", "rsantilli@sympatico.ca"),
    (55, "Savard Robert", "", "", "", "", "", "", ""),
    (56, "Sorrini C. & Perras C.", "8001 Av. De la Seine", "Anjou", "Quebec", "H1K-1V1", "514-351-9291", "514-755-7827", "chantal.perras@servicecanada.gc.ca"),
    (57, "Spector Aaron & Esther", "6795 Crois. Korczak", "Cote Saint-Luc", "Quebec", "H4W-2W7", "", "", ""),
    (58, "Syndicat d'alternative II", "22 Gabrielle-Roy", "Verdun", "Quebec", "H3E-1M3","", "", ""),
    (59, "Treville Jean & Rose", "12575 6 Av.", "Montreal", "Quebec", "H1E-1S3", "", "", ""),
    (60, "TSB Consultants", "22 Gabrielle-Roy", "Verdun", "Quebec", "H3E-1M3", "", "", "");

INSERT INTO EXPENSES(EXPENSEID, EXPENSENUMBER, DATETIME, SUPPLIER, MAINDESCRIPTION, SUBDESCRIPTION, SUBTOTAL, GST, QST, TOTAL)
    VALUES(1, 001, "2017-12-30", "Costco", "Material", "Plumbing", 23.99, 1.000, 2.000, 26.99);

INSERT INTO INVOICES(INVOICEID, INVOICENUMBER, INVOICEDATE, CLIENT, SUBTOTAL, GST, QST, TOTAL, INVOICESENT)
    VALUES(1, 2017001, "2017-12-30", "Argento Alfonso", 23.99, 1.000, 2.000, 26.99, false);