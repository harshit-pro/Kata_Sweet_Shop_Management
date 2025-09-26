# Backend Dockerfile for Render
# Build stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace/app

# Copy maven wrapper and project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Ensure wrapper is executable and build the jar (skip tests for faster image builds)
RUN chmod +x ./mvnw && ./mvnw -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /
VOLUME /tmp

# Copy the built jar from the build stage
COPY --from=build /workspace/app/target/*.jar /app.jar

# Render provides PORT env var; Spring Boot uses it via server.port=${PORT:8086}
ENV JAVA_OPTS=""

# Start the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]

# No HEALTHCHECK here; Render manages health via service checks
