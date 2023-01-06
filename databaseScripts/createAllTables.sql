CREATE TABLE users
(
	User_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    User_Name VARCHAR(50) UNIQUE,
    Password TEXT,
    Create_Date DATETIME,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP,
    Last_Updated_By VARCHAR(50)
);

CREATE TABLE contacts
(
	Contact_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Contact_Name VARCHAR(50),
    Email VARCHAR(50)
);

CREATE TABLE countries
(
	Country_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Country VARCHAR(50),
    Create_Date DATETIME,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP,
    Last_Updated_By VARCHAR(50)
);

CREATE TABLE first_level_divisions
(
	Division_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Division VARCHAR(50),
    Create_Date DATETIME,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Country_ID INT NOT NULL,
    FOREIGN KEY (Country_ID) REFERENCES countries(Country_ID)
);

CREATE TABLE customers
(
	Customer_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Customer_Name VARCHAR(50),
    Address VARCHAR(100),
    Postal_Code VARCHAR(50),
    Phone VARCHAR(50),
    Create_Date DATETIME,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Division_ID INT NOT NULL,
    FOREIGN KEY (Division_ID) REFERENCES first_level_divisions(Division_ID)
);

CREATE TABLE appointments
(
	Appointment_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(50),
    Description VARCHAR(50),
    Location VARCHAR(50),
    Type VARCHAR(50),
    Start DATETIME,
    End DATETIME,
    Create_Date DATETIME,
    Created_By VARCHAR(50),
    Last_Update TIMESTAMP,
    Last_Updated_By VARCHAR(50),
    Customer_ID INT NOT NULL,
    User_ID INT NOT NULL,
    Contact_ID INT NOT NULL,
    FOREIGN KEY (Customer_ID) REFERENCES customers(Customer_ID),
    FOREIGN KEY (User_ID) REFERENCES users(User_ID),
    FOREIGN KEY (Contact_ID) REFERENCES contacts(Contact_ID)
);

