#squares

## squares-backend

Java Spring Boot application exposing REST services. Can be run as a standalone Java application. Uses embeded Tomcat application server and embedded in-memory H2 database. Main technologies used:
- Spring Framework as IoC container 
- Spring MVC for REST controllers
- Jackson for JSON serialization 
- JPA/Hibernate for persistence layer

### How to build and run

Prerequisites:

- Java 1.7 (tested with 1.7.0_79)
- Apache Maven 3 (tested with 3.0.5)

```
mvn clean package
```

```
java -jar ./target/squares-backend-0.0.1-SNAPSHOT.jar
```

Application runs on http://localhost:8080/

## squares-frontend

Angular 2.2 single page web application. Provides UI, invokes REST services served by squares-backend.

### How to build and run

Prerequisites:

- npm (tested with 3.10.8)

```
npm install
```

```
npm start
```

Application runs on http://localhost:3000/

Application comes with prepopulated sample data.

