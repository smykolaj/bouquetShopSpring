BouquetShop is a Java-based web application built with Spring Boot. It represents a backend system that manages domain entities and business logic, supporting a bouquet ordering system.

The project is developed using Java 17 and built with Maven. It follows a clean project structure with domain-driven design principles and includes both backend logic and basic frontend elements like static image assets.

What this project uses:

Java 17
Spring Boot (version 3.5.0)
Maven for build and dependency management
Thymeleaf for simple frontend rendering
JPA and Hibernate for persistence
YAML-based configuration
Static image resources for bouquets or products
Main features:

Entity-based domain structure (e.g., for flowers, bouquets, accessories, and users)
Persistence layer using JPA
REST endpoints and backend logic for user interaction
Thymeleaf templates and static assets for presentation
Predefined bouquet images included in the static folder
Configured to run as a Spring Boot application
Project layout:

The src/main directory includes all Java classes, templates, and configuration files. Static images (like product pictures) are located under resources/static/images/. The application is configured through a application.yml file.

To run the project:

The project is designed to be run with Maven using standard Spring Boot practices. It likely supports database interaction and CRUD operations over the bouquet domain model.

Author:
Mykola Sukonnik
