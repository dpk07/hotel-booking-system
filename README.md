# Hotel-Booking-System
Hotel booking system to help receptionists 
## Running Hotel-Booking-System locally
Hotel booking system is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:


```
git clone https://github.com/dpk07/hotel-booking-system.git
cd hotel-booking-system
./mvnw package
java -jar target/*.jar
```

You can then access the documentation here: http://localhost:8080/swagger-ui.html

## Database configuration

In its default configuration, Hotel-Booking-System uses an in-memory database (H2) which
gets populated at startup with data. The h2 console is automatically exposed at `http://localhost:8080/h2-console`
and it is possible to inspect the content of the database using the `jdbc:h2:mem:testdb` url.

## Working with Hotel-Booking-System in your IDE

### Prerequisites
The following items should be installed in your system:
* Java 11 or newer.
* git command line tool (https://help.github.com/articles/set-up-git)
* Your preferred IDE 
  * Eclipse with the m2e plugin. Note: when m2e is available, there is an m2 icon in `Help -> About` dialog. If m2e is
  not there, just follow the install process here: https://www.eclipse.org/m2e/
  * [Spring Tools Suite](https://spring.io/tools) (STS)
  * IntelliJ IDEA
  * [VS Code](https://code.visualstudio.com)

### Steps:

1) On the command line
    ```
    git clone https://github.com/dpk07/hotel-booking-system.git
    ```
2) Inside Eclipse or STS
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

    Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the Hotel-Booking-System [pom.xml](pom.xml). Click on the `Open` button.

    A run configuration named `HotelBookingApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right clicking on the `HotelBookingApplication` main class and choosing `Run 'HotelBookingApplication'`.

4) Navigate to Hotel Booking Documentation

    Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in your browser.

## Default password

In its default configuration, all the urls need authentication.
The default username is `username` and the default password is `password`.


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Main Class | [HotelBookingApplication](https://github.com/dpk07/hotel-booking-system/blob/master/src/main/java/com/deepak/HotelBooking/HotelBookingApplication.java) |
|Properties Files | [application.properties](https://github.com/dpk07/hotel-booking-system/blob/master/src/main/resources/application.properties) |
|Default DB Seed Data| [data.sql](https://github.com/dpk07/hotel-booking-system/blob/master/src/main/resources/data.sql) |
