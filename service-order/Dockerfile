# Use an official OpenJDK image for Java 21
FROM openjdk:21-jdk-slim AS builder

# Set the working directory
WORKDIR /app

# Copy Gradle files and application source code
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts build.gradle.kts
COPY settings.gradle.kts settings.gradle.kts
COPY src src

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Build the application with bootJar
RUN ./gradlew clean bootJar

# Create a lightweight image to run the app with Java 21
FROM openjdk:21-jdk-slim

# Set the working directory for the application
WORKDIR /app

# Copy the built jar file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Run the application
CMD ["java", "-jar", "app.jar"]
