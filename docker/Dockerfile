# -------- BUILD --------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom y luego src (mejor para cache)
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

COPY src ./src
RUN mvn -q -e -B clean package -DskipTests

# -------- RUNTIME --------
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]