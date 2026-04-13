# Product-Line Management Application

The **Product-Line Management Application** is a production-grade **Spring Boot** application designed to facilitate
effective management of product lines, record entries within those lines, generate detailed reports, and handle user
roles in an enterprise environment. Containerized using **Docker** for consistent deployment across development,
testing, and production environments, the platform streamlines manufacturing and quality control processes through
efficient data tracking and **role-based access control**.

The application leverages **PostgreSQL** as its primary relational database with **Flyway** for robust schema migration
management, while implementing **Spring Security** for secure authentication and authorization. Through its
comprehensive MVC architecture, users can create, search, and manage records across multiple product lines. The API
features **pagination** across all collection endpoints for efficient data loading, **advanced filtering** by multiple
criteria (date ranges, organization names, product names, variants, sides, and authors), and **aggregation pipelines**
for advanced analytics, including total quantity summaries, top performer identification, and records-per-line
distribution.

## Features

* **Authentication & Authorization API** powered by **Spring Security** with role-based access control (Admin, Superior,
  Operator) for secure user management and data protection.
* **Role-Based Access Control (RBAC):**
    - **ADMIN:** Full system access - manage users, lines, and all records.
    - **SUPERIOR:** View and manage records across all lines, generate reports.
    - **OPERATOR:** Create and manage records within assigned lines.
* **Comprehensive Line Management:** Full CRUD operations for product lines with validation and execution tracking.
* **Advanced Record Management with Pagination:** All collection endpoints support pagination with customizable page
  size (default: 15) and sorting parameters (`list`, `filterWithParams`).
* **Multi-Criteria Search & Filtering:** Advanced filtering across records by:
    - Date range (`start`, `end`);
    - Organization name (case-insensitive partial match with trigram indexes);
    - Product name (case-insensitive partial match);
    - Variant and side specifications;
    - Author surname (case-insensitive partial match).
* **Aggregation Pipelines & Analytics:** Advanced JPA queries for business intelligence:
    - Total quantity summation across all records (`sumAllQuantity`);
    - Top performer identification based on record count (`findTopAuthor`);
    - Records-per-line distribution statistics (`countRecordsByLineId`);
    - Total records and users count for dashboard metrics.
* **Excel Report Generation:** Dynamic generation of downloadable Excel reports (`all_lines_report.xlsx`) with
  aggregated data by product lines and date ranges.
* **Database Performance Optimization:** Strategic indexes for query performance (`idx_record_author`, `idx_record_org`,
  `idx_record_product`, etc.)
* **Advanced Bean Lifecycle Management:** Implemented Method Injection using `@Lookup` and `ObjectProvider` to handle
  stateful prototype-scoped components (`ExecutionTracker`, `LineValidator`) within singleton services, enabling
  per-operation tracking and validation without shared state conflicts.
* **Unified Exception Handling:** Centralized error management with custom exception types (`ResourceNotFoundException`,
  `ValidationException`, `EntityAlreadyExistsException`) and global exception handling.
* **Reduce boilerplate code** using **Lombok** (`@AllArgsConstructor`, `@Getter`, `@Setter`, etc.).
* **Persist data using PostgreSQL** with ACID compliance and relational integrity through foreign key constraints.
* **Database Migration Management:** Automated schema evolution using **Flyway** with versioned migration scripts.
* **Containerized Deployment with Docker:** Seamlessly build and run the entire ecosystem — including the Spring Boot
  application and PostgreSQL database — using Docker and Docker Compose for consistent, "one-command" environment setup.

## Requirements

The following configurations are required to launch the project:

- **Java Platform (JDK)**: 25
- **Gradle**: 9.2.0
- **Spring Boot**: 4.0.2
- **Docker**: 29.1.3
- **PostgreSQL**: 17

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/Bohdan100/product-line-api
   cd product-line-api

2. Build and Run the Application Using Docker in Terminal:
   ```bash
   docker-compose up -d --build app

3. Access the Application in Your Browser:
    - **Login**: [http://localhost:8080/login](http://localhost:8080/login)
    - **Main Dashboard**: [http://localhost:8080/](http://localhost:8080/)

4. Make your HTTP requests using the following endpoints (**via browser**):

### Authentication & Session Management

The application uses form-based authentication with Spring Security. After successful login, the session is maintained
via cookies.

- **Login**: [http://localhost:8080/login](http://localhost:8080/login)
    - `GET`: Displays the login form.
    - `POST`: Authenticates user with credentials.
    - **Test Credentials**:
  ```json lines
    {
      "ADMIN": {
        "username": "admin",
        "password": "admin_secret"
      },
      "SUPERIOR": {
        "username": "alice",
        "password": "alice_secret"
      },
      "OPERATOR": {
        "username": "bob",
        "password": "bob_secret"
      }
    }
  ```

- **Logout**: [http://localhost:8080/logout](http://localhost:8080/logout)
    - `POST`: Logs out the current user and invalidates the session.

### Endpoints

1. **Line Management Endpoints**

- **Base URL**: [http://localhost:8080/lines](http://localhost:8080/lines)

  | Method | Endpoint | Description | Access |
  |--------|----------|-------------|--------|
  | GET | `/lines` | View all product lines | Authenticated users |
  | GET | `/lines/create` | Display line creation form | ADMIN only |
  | POST | `/lines/create` | Create a new product line | ADMIN only |
  | GET | `/lines/update/{id}` | Display line update form | ADMIN only |
  | POST | `/lines/update` | Update existing line | ADMIN only |
  | GET | `/lines/delete/{id}` | Delete a product line | ADMIN only |

**Request Body Example (Create/Update Line)**:

  ```json lines
    {
      "name": "Manufacturing Line A"
    }
  ```

2. **Record Management Endpoints**

- **Base URL**: [http://localhost:8080/lines/line/{lineId}](http://localhost:8080/lines/line/{lineId})

  | Method | Endpoint | Description | Access |
  |--------|----------|-------------|--------|
  | GET | `/lines/line/{lineId}` | View all records for specific line with filtering | Authenticated users |
  | GET | `/lines/line/{lineId}/record-create` | Display record creation form | Authenticated users |
  | POST | `/lines/line/{lineId}/record-create` | Create new record in line | Authenticated users |
  | GET | `/lines/line/{lineId}/record-update/{id}` | Display record update form | ADMIN, SUPERIOR |
  | POST | `/lines/line/{lineId}/record-update/{id}` | Update existing record | ADMIN, SUPERIOR |
  | GET | `/lines/line/{lineId}/record-delete/{id}` | Delete a record | ADMIN, SUPERIOR |

**Query Parameters for Record Filtering**:

| Parameter            | Required | Format     | Description                        |
|----------------------|----------|------------|------------------------------------|
| `start`              | No       | YYYY-MM-DD | Filter records from this date      |
| `end`                | No       | YYYY-MM-DD | Filter records until this date     |
| `nameOfOrganization` | No       | string     | Partial match on organization name |
| `nameOfProduct`      | No       | string     | Partial match on product name      |
| `variant`            | No       | string     | Product variant filter             |
| `side`               | No       | string     | Side specification filter          |
| `surname`            | No       | string     | Author surname partial match       |
| `page`               | No       | integer    | Page number (default: 0)           |
| `size`               | No       | integer    | Items per page (default: 15)       |

**Request Body Example (Create/Update Record)**:

  ```json lines
    {
      "date": "2026-01-01",
      "startTime": "08:00:00",
      "endTime": "16:00:00",
      "nameOfOrganization": "Next Systems",
      "nameOfProduct": "Raw Materials",
      "variant": "Standard",
      "side": "Large",
      "quantity": 1200
      }
  ```

**Example:**
`GET /lines/line/1?start=2026-01-01&end=2026-01-31&nameOfOrganization=NexLine&page=0&size=15`

3. **User Management Endpoints (Admin Only)**

- **Base URL**: [http://localhost:8080/users](http://localhost:8080/users)

  | Method | Endpoint | Description | Access |
  |--------|----------|-------------|--------|
  | GET | `/users` | List all users | ADMIN only |
  | GET | `/users/create` | Display user creation form | ADMIN only |
  | POST | `/users/create` | Create new user with roles | ADMIN only |
  | GET | `/users/update/{id}` | Display user update form | ADMIN only |
  | POST | `/users/update` | Update existing user | ADMIN only |
  | GET | `/users/delete/{id}` | Delete a user | ADMIN only |

**Request Body Example (Create User)**:

  ```json lines
      {
        "username": "mark_worker",     // 3-15 characters
        "password": "secret_password", // 3-15 characters
        "name": "Mark",                // 1-25 characters
        "surname": "Taylor",           // 1-25 characters
        "roles": ["OPERATOR"]
      }
  ```

Available Roles:

- `ADMIN` - Full system access
- `SUPERIOR` - Management and reporting access
- `OPERATOR` - Basic operational access

4. **Reporting Endpoints**

- **Base URL**: [http://localhost:8080/reports](http://localhost:8080/reports)

  | Method | Endpoint | Description | Access |
  |--------|----------|-------------|--------|
  | GET | `/reports/report-for-time` | View aggregated report by date range | ADMIN, SUPERIOR |
  | GET | `/reports/report-for-time/download-excel` | Download Excel report | ADMIN, SUPERIOR |

**Query Parameters:**

  | Parameter | Required            | Format     | Description                  |
  |-----------|---------------------|------------|------------------------------|
  | `start`   | Yes (for filtering) | YYYY-MM-DD | Start date for report period |
  | `end`     | Yes (for filtering) | YYYY-MM-DD | End date for report period   |

Example:

- `GET /reports/report-for-time?start=2024-01-01&end=2024-12-31`
- `GET /reports/report-for-time/download-excel?start=2024-01-01&end=2024-12-31`

The report generates a summary of quantities grouped by product lines and organizations within the specified date range.

5. **Dashboard Endpoint**

   - **Base URL**: [http://localhost:8080/admin/dashboard](http://localhost:8080/admin/dashboard)

   | Method | Endpoint           | Description                                  | Access     |
   |--------|--------------------|----------------------------------------------|------------|
   | GET    | `/admin/dashboard` | Main dashboard with statistics and line list | ADMIN only |

The dashboard displays:

- Total number of records in the system
- Total quantity of all products
- Number of product lines
- Total registered users
- Top performer (user with most records)
- Distribution of records across product lines

**Testing the application**:

   ```bash
   .\gradlew test            (for Windows)
   ./gradlew test            (for Linux)
   ```

**Technology Stack**:

- Backend Framework: Spring Boot 4.0.2
- Language: Java 25
- Build Tool: Gradle 9.2.0
- Database: PostgreSQL 17
- Migration Tool: Flyway
- Security: Spring Security with form-based authentication
- Containerization: Docker & Docker Compose
- Libraries: Lombok, Apache Commons IO, OpenCSV (for Excel generation)
- Testing: JUnit 5, Mockito