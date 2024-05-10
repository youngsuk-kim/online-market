# Use an official OpenJDK image for Java
FROM openjdk:21-jdk-slim

# Set the working directory for the application
WORKDIR /app

# Copy the built jar file and the pinpoint agent into the container
COPY build/libs/*.jar app.jar

COPY /pinpoint pinpoint

# Set environment variables for Pinpoint
ENV PINPOINT_AGENT_ID=bread-customer-agent
ENV PINPOINT_APPLICATION_NAME=bread-customer-service
ENV SPRING_PROFILES_ACTIVE=local

# Run the application with Pinpoint agent
CMD ["java", "-javaagent:pinpoint/pinpoint-bootstrap-2.2.3-NCP-RC1.jar", "-Dpinpoint.agentId=${PINPOINT_AGENT_ID}", "-Dpinpoint.applicationName=${PINPOINT_APPLICATION_NAME}", "-jar", "app.jar"]
