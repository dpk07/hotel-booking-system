INSERT INTO HOTEL (ADDRESS, CITY, COUNTRY,NAME,PHONE_NUMBER,STATE,ZIP_CODE) VALUES
('Savarkar Road', 'Dombivli', 'India','Modern','123456','Maharashtra','421201'),
('Shivaji Nagar', 'Kalyan', 'India','Pride','1234567','Maharashtra','421204');

INSERT INTO ROOM_TYPE (HOTEL_ID, NAME, PRICE) VALUES
('1', 'Maharaja', '3000'),
('2', 'Deluxe', '2500');

INSERT INTO ROOM (HOTEL_ID, NAME, ROOM_TYPE_ID) VALUES
('1', 'A101', '1'),
('1', 'A102', '1'),
('2', 'A103', '2');

INSERT INTO RECEPTIONIST (NAME, HOTEL_ID,USER_NAME,PASSWORD) VALUES
('Deepak', '1','dpk07','$2y$10$HHA2gOcFwLy0LOm0zAPKM.pDR/PXK8PVP0oVZyg/H15he6j1X/WPa'),('Deepak', '2','dpk09','$2y$10$HHA2gOcFwLy0LOm0zAPKM.pDR/PXK8PVP0oVZyg/H15he6j1X/WPa');


INSERT INTO CUSTOMER (FULL_NAME, EMAIL_ID,PHONE_NUMBER,PAN_NUMBER) VALUES
('Deepak', 'paidpk07@gmail.com','72083813','abcdefghijklm');

