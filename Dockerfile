# Stage 1: Build with Maven + JDK 21
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Copy source code
COPY src ./src
# Build the application (skip tests by default for Docker build)
RUN mvn clean package -DskipTests

# Stage 2: Run Spring Boot
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Create a non-root user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Copy the built JAR from the build stage
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Use optimized JVM options for containers
ENTRYPOINT ["java", "-jar", "/app/app.jar"]