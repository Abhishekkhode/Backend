# Stage 1: Build the Spring Boot application
# Use Java 17 JDK for compilation
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make Maven wrapper executable
RUN chmod +x ./mvnw
# Clean package and skip tests for faster build
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
# Use Java 17 JRE for running the application (smaller image size)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot will run on (default 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]