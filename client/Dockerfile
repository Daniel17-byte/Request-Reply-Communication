# syntax=docker/dockerfile:1
FROM openjdk:22-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and install dependencies (this allows Docker to cache this layer)
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .
COPY .mvn/ .mvn/

# Copy the source code
COPY src ./src

# Grant execute permission to mvnw
RUN chmod +x mvnw

RUN ls -la /app

# Package the application
RUN ./mvnw clean package -DskipTests

# Create the final image using a smaller base image
FROM openjdk:22-jdk-slim AS final

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Install JavaFX dependencies
RUN apt-get update && \
    apt-get install -y libgl1-mesa-glx libglu1-mesa libxi6 libxrender1 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Expose the port that the application runs on (if needed)
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
