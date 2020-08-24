INSERT INTO HOTEL (ADDRESS, CITY, COUNTRY,NAME,PHONE_NUMBER,STATE,ZIP_CODE) VALUES
('Savarkar Road', 'Dombivli', 'India','Modern','123456','Maharashtra','421201'),
('Shivaji Nagar', 'Kalyan', 'India','Pride','1234567','Maharashtra','421204');

INSERT INTO ROOM_TYPE (HOTEL_ID, NAME, PRICE) VALUES
('1', 'Maharaja', '3000'),
('1', 'Deluxe', '4100'),
('1', 'Regular', '2000'),
('1', 'Honeymoon', '4500'),
('1', 'Dorm', '1000'),
('1', 'Single Occupancy', '1500'),
('1', 'Family', '2500'),
('1', 'Double occupancy', '2800');

INSERT INTO ROOM (HOTEL_ID, NAME, ROOM_TYPE_ID) VALUES
('1', 'M101', '1'),     --1
('1', 'M102', '1'),     --2
('1', 'M103', '1'),     --3
('1', 'D201', '2'),     --4
('1', 'D202', '2'),     --5
('1', 'D203', '2'),     --6
('1', 'R301', '3'),     --7
('1', 'H302', '4'),     --8
('1', 'Do303', '5'),    --9
('1', 'S304', '6');     --10

INSERT INTO RECEPTIONIST (NAME, HOTEL_ID,USER_NAME,PASSWORD) VALUES
('Test User', '1','username','$2y$10$HHA2gOcFwLy0LOm0zAPKM.pDR/PXK8PVP0oVZyg/H15he6j1X/WPa'),
('Ramesh', '2','ramesh','$2y$10$HHA2gOcFwLy0LOm0zAPKM.pDR/PXK8PVP0oVZyg/H15he6j1X/WPa');


INSERT INTO CUSTOMER (FULL_NAME, EMAIL_ID,PHONE_NUMBER,PAN_NUMBER) VALUES
('Deepak', 'Deepak@gmail.com','720838131','abcdefghijklm'),  --1
('Rahul', 'Rahul@gmail.com','720838132','abcdefghijkl'),    --2
('Manish', 'Manish@gmail.com','720838133','abcdefghijk'),  --3
('Rekha', 'Rekha@gmail.com','720838134','abcdefghij'),    --4
('Abhijit', 'Abhijit@gmail.com','720838135','abcdefghi');--5

INSERT INTO BOOKING (HOTEL_ID,ROOM_ID,CUSTOMER_ID,RECEPTIONIST_ID,NUMBER_OF_NIGHTS,PRICE,START_DATE,END_DATE) VALUES
('1', '1','1','1','3','12000','2020-12-17','2020-12-20'),
('1', '2','2','1','3','18000','2020-12-17','2020-12-20'),
('1', '3','3','1','3','22000','2020-12-17','2020-12-20'),
('1', '4','4','1','3','32000','2020-12-17','2020-12-20'),
('1', '5','1','1','2','12000','2020-12-15','2020-12-17'),
('1', '6','2','1','15','18000','2020-12-10','2020-12-25'),
('1', '3','1','1','1','22000','2020-12-20','2020-12-21'),
('1', '1','4','1','3','22000','2020-12-21','2020-12-24'),
('1', '7','4','1','2','12000','2020-12-15','2020-12-17'),
('1', '8','3','1','15','18000','2020-12-10','2020-12-25'),
('1', '9','2','1','1','22000','2020-12-20','2020-12-21'),
('1', '10','4','1','3','22000','2020-12-21','2020-12-24');

