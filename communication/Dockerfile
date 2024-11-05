# syntax=docker/dockerfile:1
FROM openjdk:22-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and install dependencies (this allows Docker to cache this layer)
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .
COPY .mvn/ .mvn/
COPY src ./src

# Grant execute permission to mvnw
RUN chmod +x mvnw

# Package the application
RUN ./mvnw clean package -DskipTests

# Create the final image using a smaller base image
FROM openjdk:22-jdk-slim AS final

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
