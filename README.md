# SPCA2 - Online Clothing Store System

## Introduction
This project is a full-stack online clothing store system developed as part of a Software Design Patterns assignment. It is built with Quarkus, Hibernate ORM with Panache, MySQL, and a lightweight HTML/CSS/JavaScript frontend.

The system simulates a real e-commerce environment with both customer-facing and administrator-facing functionality. The design focuses on modularity, layered architecture, reusable software patterns, and realistic store workflows such as browsing, filtering, cart management, checkout, stock control, and reviews.

## System Features

### Customer Functionality
- Register and log in
- Browse all available clothing items
- Search and filter items by:
  - title
  - manufacturer
  - category
  - item family (`SHIRT`, `JACKET`, `FOOTWEAR`)
- Sort results dynamically by:
  - title
  - manufacturer
  - category
  - price
- Add items to a shopping cart
- Remove items from the cart
- Place orders through checkout
- Receive automatic discount application during checkout
- View order history and order item details
- Leave ratings and written reviews on items
- View item ratings and customer reviews

### Administrative Functionality
- Register and log in
- Perform full CRUD operations on items
- Create and manage item families such as shirts, jackets, and footwear
- Adjust stock levels to simulate inventory management
- Search and sort stock items
- View customer details
- View customer purchase history
- View stock event and low-stock alert information

## Technology Stack
- Backend Framework: Quarkus (Java 17)
- Database: MySQL
- Test Database: H2 (in-memory)
- ORM: Hibernate ORM with Panache
- Build Tool: Maven
- Frontend: HTML, CSS, JavaScript
- Testing: JUnit, RestAssured, Quarkus test framework

This stack was chosen to provide good performance, strong developer productivity, and clean integration between the API, persistence, and frontend layers.

## Architecture
The application follows a layered architectural structure:

- Model Layer  
  Defines persistent entities such as `Item`, `Customer`, `Order`, `Cart`, and `Review`.

- DTO Layer  
  Defines request and response models used by the REST API, separating external data transfer from internal entities.

- Repository Layer  
  Encapsulates persistence operations using Panache repositories.

- Service Layer  
  Contains business logic such as registration, login, item management, filtering, sorting, cart handling, checkout, discounts, and review processing.

- Resource Layer  
  Exposes REST endpoints for customer and admin interactions.

This separation improves readability, maintainability, reuse, and testability.

## Database Design
The main entities in the system are:
- `Admin`
- `Customer`
- `Item`
- `Cart`
- `CartItem`
- `Order`
- `OrderItem`
- `Review`

Supporting enums:
- `PaymentMethod`
- `ItemType`

Key relationships:
- One customer has one cart
- One cart contains many cart items
- One customer can place many orders
- One order contains many order items
- One item can appear in many cart items, order items, and reviews
- One item can receive many reviews from customers

The use of `CartItem` and `OrderItem` as separate entities is important because it keeps temporary shopping-cart data separate from permanent transaction history.

## Design Patterns Used
The project applies several GoF and architectural patterns in the implementation.

### Strategy Pattern
Used for interchangeable item sorting behaviour.

Examples:
- `SortingTitleStrat`
- `SortingManufacturerStrat`
- `SortingCategoryStrat`
- `SortingPriceStrat`
- `SortingStockStrat`

Benefit:
- avoids one large sorting conditional
- makes new sort behaviours easy to add

### Factory Pattern
Used in two major places:

1. `ItemSortStrategyFactory`  
   Selects the correct sorting strategy at runtime.

2. `ItemFactory`  
   Creates item families through dedicated creators such as:
   - `ShirtItemCreator`
   - `JacketItemCreator`
   - `FootwearItemCreator`

Benefit:
- centralises object creation
- reduces coupling
- supports future extension of item families and behaviours

### Facade Pattern
Implemented through `AdminFacade`.

Benefit:
- provides a single entry point for admin-related operations
- simplifies interaction between the API layer and multiple services

### Filter Pattern
Used for flexible multi-criteria item searching.

Examples:
- `TitleFilter`
- `ManufacturerFilter`
- `CategoryFilter`
- `ItemTypeFilter`
- `ItemFilterHandler`

Benefit:
- keeps search logic modular and composable
- allows multiple filters to be combined dynamically

### Observer Pattern
Used for stock-related event handling.

Examples:
- `StockInventManager`
- `ObserveLowStock`
- `ObserveChangeOfStock`

Benefit:
- decouples stock-changing logic from stock-monitoring behaviour
- supports multiple reactions to inventory changes

### Decorator Pattern
Used in the discount subsystem during checkout.

Examples:
- `DiscountStrategy`
- `NoDiscountStrategy`
- `AbstractDiscountDecorator`
- `BulkOrderDiscountDecorator`
- `LoyaltyDiscountDecorator`
- `CashOnDeliveryDiscountDecorator`

Benefit:
- allows multiple discounts to be combined dynamically
- keeps pricing logic extensible

### Template Method Pattern
Used in `AbstractItemResource`.

Benefit:
- defines shared item-access workflows once
- allows admin and customer resources to customise limited steps

### DTO Pattern
Used throughout the API to separate external JSON contracts from entity classes.

Benefit:
- reduces coupling
- controls what data is accepted and returned
- supports request validation cleanly

### Repository Pattern
Implemented with classes such as:
- `ItemRepo`
- `CustomerRepo`
- `ReviewRepo`

Benefit:
- isolates persistence concerns from business logic

## Validation and Current Security Approach
The project includes request validation for important inputs such as:
- login and registration requests
- item creation and update requests
- stock update requests

Examples of enforced checks:
- valid email addresses
- minimum password length
- non-null item family
- positive item price
- non-negative stock quantity
- required item title, manufacturer, category, and description

Authentication is currently implemented in a simple coursework-friendly way using stored passwords and login verification in the service layer. This is sufficient for the assignment prototype, but future production-style improvements would include:
- password hashing
- session or token-based authentication
- stronger authorization enforcement
- role-protected endpoints

## Discounts
Customers can receive discounts automatically during checkout. These are handled on the backend rather than selected manually by the user.

Implemented discount rules:
- Bulk order discount when subtotal exceeds EUR200
- Loyalty discount from the third order onward
- Cash on Delivery discount when that payment method is used

This keeps business rules centralised and consistent.

## Running the Application

### 1. Create the Database
Create a MySQL database, for example:

```sql
CREATE DATABASE spca2;
```

### 2. Configure the Datasource
Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=YOUR_USERNAME
quarkus.datasource.password=YOUR_PASSWORD
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/SPCA1
quarkus.hibernate-orm.schema-management.strategy=update
```

Note: the current project configuration uses `SPCA1` in the JDBC URL, so either create that database name or update the URL to match your intended schema name.

### 3. Start in Development Mode
On Windows:

```powershell
.\mvnw.cmd quarkus:dev
```

Or with Maven installed:

```powershell
mvn quarkus:dev
```

Application URLs:
- App: [http://localhost:8080](http://localhost:8080)
- Dev UI: [http://localhost:8080/q/dev](http://localhost:8080/q/dev)

### 4. Package the Application
```powershell
.\mvnw.cmd package
```

## Frontend Pages
The application includes lightweight frontend pages under `src/main/resources/META-INF/resources`, including:
- `launcher.html`
- `admin-dashboard.html`
- `customer-dashboard.html`
- `cart.html`
- `orders.html`

The launcher page also lists the available customer discount opportunities.

## Testing
The project now includes automated project-specific tests instead of the default Quarkus starter tests.

The test suite currently verifies:
- customer registration and login behaviour
- validation failure for weak customer passwords
- item filtering by item family
- combined filtering by item family and manufacturer

Tests run against an in-memory H2 database, so they do not require the local MySQL database to be running.

Run the tests with:

```powershell
mvn test
```

## Evaluation and Reflection
The system successfully demonstrates the use of software design patterns in a realistic web application context. It goes beyond simple CRUD by including:
- cart management
- checkout workflow
- stock reduction and stock monitoring
- review functionality
- dynamic filtering and sorting
- item-family creation logic
- automated tests

Recent improvements include:
- item family support through `ItemType`
- registry-based factory selection
- item-type filtering
- stronger request validation
- replacement of the old Quarkus starter tests with real project tests
- clearer launcher-side feedback for login and registration failures

Potential future improvements:
- stronger authentication and authorization
- repository-level query filtering for better scalability
- expanded automated coverage for checkout and stock updates
- modern frontend framework integration
- payment gateway integration

## Conclusion
This project demonstrates how pattern-driven design and layered architecture can be used to build a structured, extensible online store application. By combining Quarkus with recognised software patterns, the system achieves a strong balance between clarity, maintainability, and practical functionality.

## References
- [Quarkus](https://quarkus.io/)
- [Hibernate ORM](https://hibernate.org/)
- [MySQL](https://www.mysql.com/)

## Author
Bryan Nnadi  
Final Year B.Sc. Business Computing
