# Stage 1: Build the application using Maven Wrapper with Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app using Java 21
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/jobportal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
