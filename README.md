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

The following configurations are required to launch the project:

- **Java Platform (JDK)**: 25
- **Gradle**: 9.2.0
- **Spring Boot**: 4.0.2
- **Docker**: 29.1.3
- **Kotlin**: 2.3.0

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Bohdan100/product-line-api
   cd product-line-api

2. Build and Run the Application Using Docker in Terminal:
   ```bash
   docker-compose up -d --build app

3. Make your HTTPS requests using the following endpoints (e.g., via **Postman**):
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