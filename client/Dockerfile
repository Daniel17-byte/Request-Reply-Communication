# syntax=docker/dockerfile:1

# Folosim imaginea de bază OpenJDK
FROM openjdk:22-jdk-slim AS build

# Setăm directorul de lucru
WORKDIR /app

# Copiem fișierele pentru Maven și instalăm dependențele (caching layer)
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .
COPY .mvn/ .mvn/

# Copiem codul sursă
COPY src ./src

# Setăm permisiuni pentru fișierul mvnw
RUN chmod +x mvnw

# Construim aplicația (generăm .jar-ul)
RUN ./mvnw clean package -DskipTests

# Creăm imaginea finală pentru rularea aplicației
FROM openjdk:22-jdk-slim AS final

# Setăm directorul de lucru
WORKDIR /app

# Copiem JAR-ul generat în faza de build
COPY --from=build /app/target/*.jar app.jar

# Instalăm dependențele necesare pentru JavaFX și X virtual framebuffer
RUN apt-get update && \
    apt-get install -y libgl1-mesa-glx libglu1-mesa libxi6 libxrender1 wget unzip xvfb && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Descărcăm și extragem SDK-ul JavaFX
RUN wget https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_linux-x64_bin-sdk.zip -O javafx.zip \
    && unzip javafx.zip -d /opt/javafx \
    && rm javafx.zip

# Setăm variabila de mediu pentru JavaFX
ENV PATH_TO_FX="/opt/javafx/javafx-sdk-17.0.2/lib"

# Expunem portul pe care rulează aplicația (dacă e necesar)
EXPOSE 8082

# Folosim Xvfb pentru a emula un display virtual
CMD ["xvfb-run", "-a", "java", "--module-path", "/opt/javafx/javafx-sdk-17.0.2/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]
