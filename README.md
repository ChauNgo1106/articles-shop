# Article Shop

## Prerequisite:
+ Install Docker Desktop on your machine
+ Install Maven (3.9.11)
+ Java JDK 21

## 🚀 Start the App

From root repo, you can use Maven wrapper (./mvnw command with your JAVA_HOME pointing to JDK +21) 
```bash
./mvnw clean package
./mvnw spring-boot:run
```
OR, using your installed maven (with JAVA_HOME pointing to JDK +21)

```bash
mvn clean package
mvn spring-boot:run
```

### [OPTIONAL] Run all testing cases
In case of you may want to run test cases manually.
```bash
mvn clean test
# OR
./mvnw clean test
```


## Endpoints
You can easily find a full details about APIs via this link after running successfully:
http://localhost:8080/swagger-ui.html

## In-memory H2 Database
Initial data load from 
```src/main/resources/data.sql```

Accessible via: http://localhost:8080/h2-console
```
jdbc url: jdbc:h2:mem:articledb

username: chaungo

password: <no password, leave it blank>
```