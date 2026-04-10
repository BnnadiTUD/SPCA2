SPCA2 – Online Clothing Store System
1. Introduction

This project presents the design and implementation of a full-stack online clothing store system, developed as part of a Software Design Patterns assignment. The system demonstrates the application of modern backend development practices using Quarkus, alongside the integration of key object-oriented design principles and design patterns.

The primary objective of this project is to simulate a real-world e-commerce environment, supporting both customer-facing functionality (browsing, searching, ordering) and administrative operations (stock management, customer oversight). Emphasis is placed on building a scalable, maintainable, and modular architecture.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

2. System Features

2.1 Customer Functionality
Browse all available clothing items
Search items using:
Title (partial matching supported)
Manufacturer
Category
Sort results dynamically by:
Title
Manufacturer
Price
Add items to a shopping cart
Place orders

2.2 Administrative Functionality
Secure admin registration and login
Perform full CRUD operations on items
Adjust stock levels to simulate inventory management
View customer details and purchase history

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3. Technology Stack

The system is implemented using the following technologies:

Backend Framework: Quarkus (Java)
Database: MySQL
ORM: Hibernate ORM with Panache
Build Tool: Maven
Frontend: HTML, CSS, JavaScript (lightweight UI for interaction)
Testing Tools: Postman

This stack was selected to ensure high performance, developer productivity, and ease of integration.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

4. System Architecture

The application follows a layered architectural pattern, ensuring separation of concerns and improved maintainability.

Model Layer
Represents persistent entities such as Item, Customer, and Order.
Repository Layer
Handles database interactions using Panache repositories.
Service Layer
Contains business logic, acting as an intermediary between repositories and controllers.
DTO Layer (Data Transfer Objects)
Ensures secure and structured data exchange between layers.
Resource Layer (REST Controllers)
Exposes API endpoints for client interaction.

This architecture promotes loose coupling, high cohesion, and testability, which are critical for scalable systems.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

5. Design Patterns Implementation

The project incorporates several Gang of Four (GoF) design patterns, demonstrating an understanding of reusable software solutions.

5.1 Strategy Pattern

The Strategy pattern is used to implement flexible sorting behaviour. Each sorting method (e.g., by price, title, or manufacturer) is encapsulated within its own strategy class.

Benefit:

Eliminates complex conditional logic
Allows new sorting strategies to be added without modifying existing code

5.2 Factory Pattern

A Factory is used to dynamically create the appropriate sorting strategy based on user input.

Benefit:

Centralises object creation
Improves extensibility and reduces coupling

5.3 Facade Pattern

The Facade pattern simplifies complex administrative operations by providing a single interface that coordinates multiple services.

Benefit:

Reduces system complexity
Improves code readability and usability

5.4 Filter Pattern (Criteria Pattern)

The Filter Pattern is applied to handle search functionality, where multiple criteria (title, manufacturer, category) can be combined dynamically.

Instead of using large conditional queries, individual filters are applied to a collection of items and combined as needed.

Example Concept:

Filter by title
Filter by category
Combine filters for refined results

Benefit:

Promotes reusable and composable filtering logic
Improves readability compared to large conditional search methods
Allows easy extension (e.g., filtering by price range or stock availability)

Justification:
Given that the system supports multi-criteria searching, the Filter pattern provides a cleaner and more scalable alternative to hardcoded query logic.

5.5 Observer Pattern

The Observer pattern is used to model event-driven behaviour within the system, particularly for scenarios such as stock updates or order processing.

For example:

When an order is placed, the system can notify:
Inventory (to update stock levels)
Admin system (to log activity)
Customer service components (for confirmation handling)

Benefit:

Decouples components (observers do not need direct dependencies)
Supports event-driven architecture
Makes the system more extensible for future features (e.g., notifications, analytics)

Justification:
The Observer pattern is appropriate for situations where multiple components must react to a single event, such as an order being created or stock being updated.

5.6 Justification

The use of these patterns improves:

Code readability
Extensibility (new features can be added with minimal changes)
Maintainability (clear separation of responsibilities)

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

6. Database Configuration

The system uses a MySQL relational database. To initialise:

CREATE DATABASE spca2;

Configure the connection in application.properties:

quarkus.datasource.db-kind=mysql
quarkus.datasource.username=YOUR_USERNAME
quarkus.datasource.password=YOUR_PASSWORD
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/spca2

quarkus.hibernate-orm.database.generation=update

The update setting ensures the schema evolves automatically during development.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

7. Running the Application
   
7.1 Development Mode
./mvnw quarkus:dev
Application URL: http://localhost:8080
Dev UI: http://localhost:8080/q/dev

Development mode enables live reload, significantly improving development efficiency.

7.2 Packaging and Execution
./mvnw package

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

8. Testing Strategy

Testing was conducted using:

Postman for API endpoint validation
Manual UI testing via HTML pages
Verification of:
CRUD operations
Search and sorting logic
Data persistence

This approach ensures that both functional correctness and data integrity are maintained.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

9. Evaluation and Reflection

The system successfully demonstrates the application of software design patterns in a real-world scenario. The layered architecture and use of patterns such as Strategy and Factory significantly improved the modularity of the system.

However, several improvements could be made:

Implementation of JWT-based authentication
Enhanced frontend using a modern framework (e.g., React)
Persistent shopping cart functionality
Integration of payment processing

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

10. Conclusion

This project highlights the importance of structured design and pattern-driven development in building scalable applications. By combining Quarkus with established design principles, the system achieves a balance between performance, maintainability, and extensibility.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------

11. References
    
https://quarkus.io/
https://hibernate.org/
https://www.mysql.com/
13. Author

Bryan Nnadi
Final Year B.Sc. Business Computing
