# сборка
FROM maven:3.9.2-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# релиз
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/WeatherForecast-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
