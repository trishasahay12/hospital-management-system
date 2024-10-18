CREATE DATABASE hospital_management;
drop database hospital_management;
USE hospital_management;
drop table appointments;

CREATE TABLE appointments(
appointment_id INT,
patient_id INT,
doctor_id INT,
appointment_date DATE,
description TEXT);

CREATE TABLE doctors(
doctor_id INT,
firstName VARCHAR(20),
lastName VARCHAR(20),
specialization VARCHAR(50),
contactNumber VARCHAR(15));

CREATE TABLE patients(
patient_id INT,
firstName VARCHAR(20),
lastName VARCHAR(20),
dateOfBirth DATE,
gender VARCHAR(10),
contactNumber VARCHAR(15),
address VARCHAR(225));

-- SELECT * FROM ;

INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_date, description)
VALUES
  (1, 101, 2001, '2023-10-25', 'General checkup'),
  (2, 102, 2002, '2023-10-26', 'Dental cleaning'),
  (3, 103, 2001, '2023-10-27', 'Prescription refill'),
  (4, 104, 2003, '2023-10-28', 'Surgery consultation'),
  (5, 101, 2002, '2023-10-29', 'Follow-up appointment'),
  (6, 105, 2004, '2023-11-01', 'Allergy testing'),
  (7, 102, 2003, '2023-11-02', 'X-ray'),
  (8, 103, 2001, '2023-11-03', 'Blood test'),
  (9, 104, 2002, '2023-11-04', 'Physical therapy'),
  (10, 105, 2003, '2023-11-05', 'Mental health consultation');
  
  
  
INSERT INTO doctors (doctor_id, firstName, lastName, specialization, contactNumber)
VALUES
  (2001, 'John', 'Doe', 'General Medicine', '123-456-7890'),
  (2002, 'Jane', 'Smith', 'Dentistry', '987-654-3210'),
  (2003, 'David', 'Johnson', 'Surgery', '555-123-4567'),
  (2004, 'Emily', 'Brown', 'Allergy and Immunology', '777-888-9999');
  
INSERT INTO patients (patient_id, firstName, lastName, dateOfBirth, gender, contactNumber, address)
VALUES
  (101, 'Alice', 'Cooper', '1990-01-01', 'Female', '11-22-3333', '123 Main St'),
  (102, 'Bob', 'Dylan', '1991-02-02', 'Male', '44-55-6666', '456 Elm St'),
  (103, 'Charlie', 'Chaplin', '1992-03-03', 'Male', '77-88-9999', '789 Oak St'),
  (104, 'Diana', 'Ross', '1993-04-04', 'Female', '12-45-7890', '101 Pine St'),
  (105, 'Elvis', 'Presley', '1994-05-05', 'Male', '55-12-4567', '112 Cedar St');



