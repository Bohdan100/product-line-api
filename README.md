# Product-Line Management Application
The **Product-Line Management Application** is a Spring Boot application designed to facilitate effective management of product lines, record entries within those lines, generate detailed reports, and handle user roles in an enterprise environment.

## Features
- Authentication via email and password. 
- Add, update, view, and delete product lines and records. 
- Search records by multiple criteria, such as creation date, organization name, product name, variant, side, and surname. 
- Generate reports based on product line data.
- Personalized access based on user roles (Admin, Operator, Superior).
- Admin capabilities to manage users (create, update, delete).
- Persist data using PostgreSQL. 
- Handle database migrations with Flyway. 
- Streamline setup and build processes with Gradle. 
- Reduce boilerplate code using Lombok. 
- Ensure code quality with JUnit 5 and Mockito tests.

## Requirements
The application is built using the following technologies:
- **Spring Boot**: 3.3.5
- **Java Platform (JDK)**: 21
- **PostgreSQL**: 16.6
- **Flyway**: 11.0.1
- **Thymeleaf**: 3.4.1
- **Lombok**: 1.18.36
- **Apache POI**: 5.4.0
- **JUnit**: 5
- **Mockito**: 5
- **Gradle**: 8.8
  
## Database Setup
Before running the application, follow these steps to set up the database:

1. **Create a PostgreSQL 16 Database**  
   Set up a PostgreSQL database to store the application’s data.

2. **Configure Database and User**  
   Perform the following steps in your PostgreSQL instance to create a user and database for the application:

    1. Create a new user with a password:
       ```sql
       CREATE USER IF NOT EXISTS product_admin WITH PASSWORD 'secret1234';
       ```

    2. Create a new database:
       ```sql
       CREATE DATABASE IF NOT EXISTS product ENCODING 'UTF8' OWNER product_admin;
       ```

3. **Connect to the Database**  
   To connect to the `product` database as the `product_admin` user, use the following command in the terminal:
   ```bash
   psql -U product_admin -d product

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Bohdan100/product-line-api
   cd product-line-api

2. Build and Run the Application Using Gradle in Terminal:
   ```bash
   .\gradlew bootRun     (for Windows)
   ./gradlew bootRun     (for Linux)
    ```
3. Build and Run the Application Using a JAR File:
   ```bash
   .\gradlew bootJar     (for Windows)
   ./gradlew bootJar     (for Linux)
   
   java -jar product-line-api.jar
    ```
4. Access the Application in Your Browser:
   - **Login**: [http://localhost:8080/login](http://localhost:8080/login)

   - **Application**:
       - Main page: [http://localhost:8080/](http://localhost:8080/)
       - Line Management: [http://localhost:8080/lines](http://localhost:8080/lines)
       - User Management: [http://localhost:8080/users](http://localhost:8080/users)
       - Record Management within the Line: [http://localhost:8080/lines/line/{lineId}](http://localhost:8080/lines/line/{lineId})
       - Report (create and download): [http://localhost:8080/reports/report-for-time](http://localhost:8080/reports/report-for-time)
5. Testing the application:
   ```bash
   .\gradlew test            (for Windows)
   ./gradlew test            (for Linux)
   ```